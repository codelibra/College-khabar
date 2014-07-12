package com.blogger;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.blogger.model.Post;
import com.google.gson.JsonObject;

public class AsyncLoadPost extends AsyncTask<String, Void, Post>
{

	private static final String TAG = "AsyncLoadPostList";
	private final PostDisplayActivity postDisplayActivity;
	private final ProgressDialog dialog;
	private com.google.api.services.blogger.Blogger service;

	AsyncLoadPost(PostDisplayActivity postDisplayActivity)
	{
		this.postDisplayActivity = postDisplayActivity;
		service = postDisplayActivity.service;
		dialog = new ProgressDialog(postDisplayActivity);
	}

	@Override
	protected void onPreExecute()
	{
		dialog.setMessage("Loading post list...");
		dialog.show();
	}

	@Override
	protected Post doInBackground(String... postIds)
	{
		try
		{
			String postId = postIds[0];
			return service.posts().get(VisitedActivity.BLOG_ID, postId).setFields("title,content")
					.execute();
		}
		catch (IOException e)
		{
			Log.e(TAG, e.getMessage());
			return new Post().setTitle(e.getMessage());
		}
	}

	@Override
	protected void onPostExecute(Post result)
	{
		dialog.dismiss();
		postDisplayActivity.display(result);
	}
}