package com.blogger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {
    EditText username, password;
    TextView signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
        /*try
		{
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
		}
		catch (Exception e)
		{
			Log.e("splash",e.getMessage());
		}
		 */
        Initialize();

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setProgressBarIndeterminateVisibility(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(login.getWindowToken(), 0);

                if (isNetworkAvailable() == false) {
                    noInternetAccess();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    return;
                }

                ParseUser.logInInBackground(username.getText().toString(), password.getText()
                        .toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {
                        setProgressBarIndeterminateVisibility(false);
                        if (e == null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    LoginActivity.this);
                            builder.setMessage(e.getMessage()).setTitle("Error!!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }


                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable() == false) {
                    noInternetAccess();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, SignUpNew.class);
                startActivity(intent);
            }
        });

    }

    private void Initialize() {
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.leaderboardbutton1);
        signup = (TextView) findViewById(R.id.process);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void noInternetAccess() {
        Toast.makeText(this, "There is NO INTERNET ACCESS!!! :(", Toast.LENGTH_LONG).show();
    }

}
