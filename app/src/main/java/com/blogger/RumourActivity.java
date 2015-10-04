package com.blogger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RumourActivity extends ListActivity {
    public boolean refresh;
    public String publicchannel;
    public ListView lv;
    public TextView group;
    public String postyear;
    public Button post, ref;
    protected List<ParseObject> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.rumour_new);
        setContentView(R.layout.new_rumour);

        post = (Button) findViewById(R.id.button1);
        ref = (Button) findViewById(R.id.button2);
        group = (TextView) findViewById(R.id.group_name);

		/*try
		{
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
		}
		catch (Exception e)
		{
			Log.e("splash", e.getMessage());
		}*/

        if (isNetworkAvailable() == false)
            noInternetAccess();

        lv = getListView();
        refresh = false;

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null)
            navigateToLogin();
        else
            Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_LONG).show();

        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PostMessage();
            }
        });


        ref.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RefreshRumours();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProgressBarIndeterminateVisibility(true);
        ParseUser currentuser = ParseUser.getCurrentUser();
        if (currentuser == null)
            return;
        String currbranch, curryear;

        currbranch = currentuser.getString(ParseConstants.KEY_BRANCH);
        curryear = currentuser.getString(ParseConstants.KEY_YEAR);
        Integer endyear = new Integer(curryear);
        Integer startyear;

        if (currbranch.contentEquals("ARCHI") == false)
            startyear = endyear - 4;
        else
            startyear = endyear - 5;

        String channel = currbranch + curryear;
        publicchannel = channel;

        group.setText(currbranch + " " + startyear + " - " + endyear);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(channel);
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);

        boolean isInCache = query.hasCachedResult();

        if (isNetworkAvailable() == true)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        else if (isNetworkAvailable() == false && isInCache == true) {
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
            noInternetAccess();
        } else {
            noInternetAccess();
            return;
        }

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                mMessages = messages;
                if (e == null) {
                    // We found messages!
                    ArrayList<String> usernames = new ArrayList<String>();
                    ArrayList<String> time = new ArrayList<String>();
                    for (ParseObject mMessage : messages) {
                        usernames.add(mMessage.getString(ParseConstants.KEY_SENDER_NAME));
                        String str = mMessage.getDate("sent_date").getHours() + ":"
                                + mMessage.getDate("sent_date").getMinutes();
                        if (str != null)
                            time.add(str);
                        else
                            time.add("00:00");
                    }

                    lv.setAdapter(new VersionAdapterRumour(RumourActivity.this, usernames, time));
                    lv.setScrollingCacheEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (isNetworkAvailable() == false)
            noInternetAccess();

        super.onListItemClick(l, v, position, id);
        ParseObject message = mMessages.get(position);
        String post = message.getString(ParseConstants.KEY_POST);
        String sendername = message.getString(ParseConstants.KEY_SENDER_NAME);

        final Dialog dialog = new Dialog(RumourActivity.this, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.rumour_custom_alert);

        final TextView userInput3 = (TextView) dialog.findViewById(R.id.rumour_custom_et);
        final TextView sender = (TextView) dialog.findViewById(R.id.sender_custom_et);
        final Button ok = (Button) dialog.findViewById(R.id.btn_ok);

        sender.setText(sendername);
        userInput3.setText(post);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            navigateToLogin();
        } else if (item.getItemId() == R.id.action_post) {
            PostMessage();
        } else if (item.getItemId() == R.id.rumour_refresh) {
            RefreshRumours();
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void noInternetAccess() {
        Toast.makeText(this, "There is NO INTERNET ACCESS!!! :(", Toast.LENGTH_LONG).show();
    }

    protected ParseObject CreateMessage(String message) {
        ParseUser currentuser = ParseUser.getCurrentUser();
        String currbranch, curryear;

        currbranch = currentuser.getString(ParseConstants.KEY_BRANCH);
        curryear = postyear;

        String channel = currbranch + curryear;
        Log.v("yearrr yahaa kya bakar hhhh!!!", channel + " " + postyear);
        ParseObject temp = new ParseObject(channel);
        temp.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
        temp.put(ParseConstants.KEY_POST, message);
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        temp.put("sent_date", d);

        return temp;
    }

    private void SendMessage(ParseObject post) {
        post.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    Toast.makeText(RumourActivity.this, "Posted :)", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RumourActivity.this);
                    builder.setMessage("Error in posting message..Send again!")
                            .setTitle("OOOppsss!!!").setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }

    private void PostMessage() {
        if (isNetworkAvailable() == false) {
            noInternetAccess();
            return;
        }

        final Dialog dialog = new Dialog(RumourActivity.this, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.custom_alert_new);
        final Button post = (Button) dialog.findViewById(R.id.btn_post);
        final Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText userInput3 = (EditText) dialog.findViewById(R.id.et_dialog);
        dialog.show();
        final InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm3.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                String names[] = {"Own batch", "1st year", "2nd year", "3rd year", "4th year"};

                final Dialog alertDialog = new Dialog(RumourActivity.this);
                alertDialog.setContentView(R.layout.alert_list);

                final ListView lv = (ListView) alertDialog.findViewById(R.id.lv);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RumourActivity.this,
                        android.R.layout.simple_list_item_1, names);

                alertDialog.setTitle("Post for:");
                lv.setAdapter(adapter);
                alertDialog.show();

                lv.setOnItemClickListener(new OnItemClickListener() {

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);

                    ParseUser currentuser = ParseUser.getCurrentUser();

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                            long arg3) {

                        if (position == 0) {
                            Toast.makeText(RumourActivity.this, "Own Batch", Toast.LENGTH_SHORT)
                                    .show();
                            postyear = currentuser.getString(ParseConstants.KEY_YEAR);
                        } else if (position == 1) {
                            Toast.makeText(RumourActivity.this, "1st year", Toast.LENGTH_SHORT)
                                    .show();
                            postyear = Integer.toString(year + 3);

                        } else if (position == 2) {
                            Toast.makeText(RumourActivity.this, "2nd year", Toast.LENGTH_SHORT)
                                    .show();
                            postyear = Integer.toString(year + 2);
                        } else if (position == 3) {
                            Toast.makeText(RumourActivity.this, "3rd year", Toast.LENGTH_SHORT)
                                    .show();
                            postyear = Integer.toString(year + 1);
                        } else if (position == 4) {
                            Toast.makeText(RumourActivity.this, "Final year", Toast.LENGTH_SHORT)
                                    .show();
                            postyear = Integer.toString(year);
                        }
                        alertDialog.dismiss();
                        Log.v("yearrrrrr", postyear);


                        setProgressBarIndeterminateVisibility(true);
                        String message = userInput3.getText().toString();
                        ParseObject post = CreateMessage(message);
                        SendMessage(post);
                    }
                });


            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        });
    }

    private void RefreshRumours() {
        if (isNetworkAvailable() == false) {
            noInternetAccess();
            return;
        }

        refresh = false;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(publicchannel);
        Date day = new Date();
        day.setDate(day.getDate() - 1);
        query.whereLessThan("createdAt", day);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ParseObject myObject;
                Log.v("hiiiiii", publicchannel + " " + refresh + objects.size());

                for (int i = 0; i < objects.size(); ++i) {
                    myObject = objects.get(i);
                    myObject.deleteInBackground();

                }
            }
        });

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);

            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}
