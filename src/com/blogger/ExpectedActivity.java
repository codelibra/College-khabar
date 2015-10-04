package com.blogger;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ExpectedActivity extends ListActivity 
{
	public List<ParseObject> mMessages;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_expected);
/*
		try
		{
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
		}
		catch (Exception e)
		{
			Log.e("splash",e.getMessage());
		}
*/
		setProgressBarIndeterminateVisibility(true);
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Expected");
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		
		boolean isInCache = query.hasCachedResult();

		if(isNetworkAvailable()==true)
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		else if(isNetworkAvailable()==false && isInCache==true)
		{
			query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY );
			noInternetAccess();
		}
		else
		{
			noInternetAccess();
			return;	
		}
		
		
		query.findInBackground(new FindCallback<ParseObject>()
		{
			@Override
			public void done(List<ParseObject> messages, ParseException e)
			{
				setProgressBarIndeterminateVisibility(false);
				mMessages = messages;
				if (e == null)
				{
					String[] usernames = new String[messages.size()];
					int i = 0;
					for (ParseObject mMessage : messages)
					{
						usernames[i] = mMessage.getString("company_name");
						i++;
					}

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExpectedActivity.this,
							android.R.layout.simple_list_item_1, usernames);
					setListAdapter(adapter);
				}
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		if(isNetworkAvailable()==false)
			noInternetAccess();
		
		super.onListItemClick(l, v, position, id);
		ParseObject temp = mMessages.get(position);
		String company = "", money = "undisclosed", profile = "undisclosed", eligbility = "undisclosed", cgpa = "undisclosed", date, message;

		company = temp.getString("company_name");
		money = temp.getString("package");
		eligbility = temp.getString("eligibility12");
		cgpa = temp.getString("cgpa");
		date = temp.getString("expected_date");

		message = company + "\nPackage~: " + money + "\nEligibility Board , CG: " + eligbility + " " + cgpa
				+ "\nDate: " + date;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message).setTitle("All The Best")
				.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.expected, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if(isNetworkAvailable()==false)
		{
			noInternetAccess();
			return super.onMenuItemSelected(featureId, item);
		}
		
		if (item.getItemId() == R.id.action_refresh)
		{
			if (Build.VERSION.SDK_INT >= 11)
			{
				recreate();
			}
			else
			{
				Intent intent = getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				overridePendingTransition(0, 0);

				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private boolean isNetworkAvailable()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	

	private void noInternetAccess()
	{
		Toast.makeText(this, "There is NO INTERNET ACCESS!!! :(", Toast.LENGTH_LONG).show();
	}

}
