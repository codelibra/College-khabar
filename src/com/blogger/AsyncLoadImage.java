package com.blogger;


import java.io.InputStream;

import android.R.anim;
import android.R.id;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncLoadImage extends AsyncTask<String, Void, Bitmap>
{
	public String url;
	public CareercupActivity adp;

	AsyncLoadImage(CareercupActivity va, String img)
	{
		url = img;
		adp = va;
	}

	@Override
	protected Bitmap doInBackground(String... url)
	{
		String urldisplay = url[0];
		Bitmap mIcon11 = null;

		try
		{
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
			if (mIcon11 == null)
				Log.v("arey yr null h image!!!", "null aai image behnchod!!");
		}
		catch (Exception e)
		{
			Log.e("Error aaa gai bhnchd", e.getMessage() + " ");
			e.printStackTrace();
			return null;
		}
		return mIcon11;
	}


}
