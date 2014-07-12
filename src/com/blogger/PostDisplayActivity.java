package com.blogger;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.extensions.android3.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.GoogleKeyInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.blogger.model.Post;

public class PostDisplayActivity extends Activity
{

	private static String TAG = "PostDisplay";
	WebView w;
	private static final Level LOGGING_LEVEL = Level.ALL;
	final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	final JsonFactory jsonFactory = new AndroidJsonFactory();
	com.google.api.services.blogger.Blogger service;
	public VisitedActivity sendcontent;
	public String postId;
	public String title;
	public String content;
	public String returncontent;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_display);
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

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			postId = extras.getString(VisitedActivity.POST_ID_KEY);
			title = extras.getString(VisitedActivity.POST_ID_TITLE);
			content = extras.getString(VisitedActivity.POST_CONTENT);
			int presence = extras.getInt("present");
			if (presence == 0)
			{
				Log.v(TAG, "postId: " + postId);

				ClientCredentials.errorIfNotSpecified();
				service = new com.google.api.services.blogger.Blogger.Builder(transport,
						jsonFactory, null)
						.setApplicationName("Google-BloggerAndroidSample/1.0")
						.setJsonHttpRequestInitializer(
								new GoogleKeyInitializer(ClientCredentials.KEY)).build();

				Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);
				new AsyncLoadPost(this).execute(postId);
			}
			else
				displayFromDB(content);
		}
	}

	@Override
	public void onBackPressed()
	{
		Intent returnIntent = new Intent();
		returnIntent.putExtra("title", title);
		returnIntent.putExtra("saved", returncontent);
		setResult(RESULT_OK, returnIntent);
		finish();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void display(Post result)
	{
		((TextView) findViewById(R.id.title)).setText(result.getTitle());
		w = (WebView) findViewById(R.id.content);
		w.getSettings().setLoadWithOverviewMode(true);
		/*w.setHorizontalScrollBarEnabled(false);
		w.setOnTouchListener(new View.OnTouchListener()
		{
			float m_downX;
			public boolean onTouch(View v, MotionEvent event)
			{
				
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					// save the x
					m_downX = event.getX();
				}
					break;

				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
				{
					// set x so that it doesn't move
					event.setLocation(m_downX, event.getY());
				}
					break;

				}

				return false;
			}

		});
		*/
		returncontent = result.getContent();
		w.loadDataWithBaseURL(null, returncontent, "text/html", "utf-8", null);
	}

	public void displayFromDB(String content)
	{
		((TextView) findViewById(R.id.title)).setText(title);
		w = (WebView) findViewById(R.id.content);
		w.getSettings().setLoadWithOverviewMode(true);
		w.setHorizontalScrollBarEnabled(false);
		w.setOnTouchListener(new View.OnTouchListener()
		{
			float m_downX;
			public boolean onTouch(View v, MotionEvent event)
			{
				
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					// save the x
					m_downX = event.getX();
				}
					break;

				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
				{
					// set x so that it doesn't move
					event.setLocation(m_downX, event.getY());
				}
					break;

				}

				return false;
			}

		});
		
		w.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
	}

}
