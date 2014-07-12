package com.blogger;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ViewLeaderboard extends ListActivity
{
	public ListView lv;
	public List<ParseUser> mMessages;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_view_leaderboard);
		/*try
		{
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
		}
		catch (Exception e)
		{
			Log.e("splash",e.getMessage());
		}*/

		lv = getListView();

		setProgressBarIndeterminateVisibility(true);
		ParseQuery<ParseUser> query = ParseUser.getQuery();

		query.addDescendingOrder(ParseConstants.KEY_SCORE);

		boolean isInCache = query.hasCachedResult();

		if (isNetworkAvailable() == true)
			query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		else if (isNetworkAvailable() == false && isInCache == true)
		{
			query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
			noInternetAccess();
		}
		else
		{
			noInternetAccess();
			return;
		}

		query.findInBackground(new FindCallback<ParseUser>()
		{

			@Override
			public void done(List<ParseUser> messages, ParseException e)
			{
				mMessages = messages;
				setProgressBarIndeterminateVisibility(false);
				if (e == null)
				{
					List<String> usernames = new ArrayList<String>();

					int i = 0;
					for (ParseObject mMessage : messages)
					{
						usernames.add((i + 1) + "." + mMessage.getString("username") + " "
								+ mMessage.get(ParseConstants.KEY_TESTS)
								+ mMessage.get(ParseConstants.KEY_SCORE)
								+ mMessage.getString("branch"));
						i++;
					}

					lv.setAdapter(new VersionAdapterLeaderboard(ViewLeaderboard.this, messages));
					lv.setScrollingCacheEnabled(false);
				}
				else
					Log.v("error in query", e.getMessage());

			}
		});

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

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(ViewLeaderboard.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
