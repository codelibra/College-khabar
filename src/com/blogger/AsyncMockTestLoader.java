package com.blogger;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncMockTestLoader extends AsyncTask<URL, Integer, Elements>
{
	private MockTestActivity mactivity;
	private String load;
	private ProgressDialog dialog;

	public AsyncMockTestLoader(MockTestActivity temp, String url)
	{
		mactivity = temp;
		load = url;
	}

	@Override
	protected void onPreExecute()
	{
		dialog = new ProgressDialog(mactivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("Loading Test... Be Ready!!! :) ");
		dialog.setMax(100);
		dialog.show();
	}

	@Override
	protected Elements doInBackground(URL... urls)
	{
		String title = "null h";
		Elements content = null;

		publishProgress(5);
		publishProgress(5);
		
		Log.v("titlessssssssssssssss", load);
		try
		{
			String str = urls.toString();
			long st = System.currentTimeMillis();

			Document doc = Jsoup.connect(load).userAgent("Mozilla").timeout(100000).get();
			doc.outputSettings().charset("UTF-8");
			
			Log.v("doc content", doc.text() + "text");

			content = doc.getElementsByClass("bix-tbl-container");
			publishProgress(15);
			if (content.size() == 0)
				Log.v("total content", "ZZZZZEROO");
			else
				Log.v("total content", content.text());
			for (int i = 0; i < 20; ++i)publishProgress(5);
		}

		catch (IOException e)
		{
			e.printStackTrace();

		}

		return content;
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		dialog.incrementProgressBy(values[0]);
	}

	@Override
	protected void onPostExecute(Elements title)
	{
		dialog.dismiss();
		mactivity.Display(title);
	}
}
