package com.blogger;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class CareercupActivity extends ListActivity
{
	public String t,url;
	public Elements myelements;
	public int currentpage;
	public URL n = null;
	public int maxpages;
	public ArrayList<String> post;
	public int index;
	public Bitmap result;
	public ListView lv;
	public int clicked;
	public String Question;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_careercup);
			
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
		
		
		
		maxpages = -1;
		post = new ArrayList<String>();
		myelements = new Elements();
		lv = getListView();
		currentpage = 1;
		result = null;
		clicked = 0;

		url = getIntent().getExtras().getString("company");
		
		
		Log.v("urllllllllllllllllllllll",url+"hi");
		try
		{
			n = new URL(url);

		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		setProgressBarIndeterminateVisibility(true);

		new AsyncLoadCareercup(this, url, maxpages).execute(n);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.careercup, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if (item.getItemId() == R.id.nxt_img)
		{
			if (currentpage == maxpages || clicked == 1)
				return true;
			clicked = 1;
			setProgressBarIndeterminateVisibility(true);
			try
			{
				n = new URL(url);
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}

			++currentpage;
			Log.v("nxt page", url + "&n=" + currentpage);

			new AsyncLoadCareercup(this, url + "&n=" + currentpage, maxpages).execute(n);

		}
		else if (item.getItemId() == R.id.prev_img)
		{
			if (currentpage == maxpages || clicked == 1 || currentpage == 1)
				return true;
			clicked = 1;
			setProgressBarIndeterminateVisibility(true);
			try
			{
				n = new URL(url);
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}

			--currentpage;
			Log.v("nxt page", url + "&n=" + currentpage);

			new AsyncLoadCareercup(this, url + "&n=" + currentpage, maxpages).execute(n);

		}

		return super.onMenuItemSelected(featureId, item);
	}

	public void Display(int temp, Elements content, String uri)
	{
		Log.v("arey yr", "yaha b aya h bhai :) "+uri);
		clicked = 0;
		maxpages = temp;
		if (content == null)
		{
			Log.v("arey yr", "kuch ni  aya!!");
			return;
		}

		
		

		myelements.clear();
		post.clear();
		setProgressBarIndeterminateVisibility(false);
		myelements.addAll(content);

		boolean ans = false;

		String imguristr = uri;
		int N = post.size();

		if (post.size() != 0)
			post.remove(N - 1);

		int top = post.size();
		int i = 1;

		for (Element link : content)
		{
			if (ans == true)
			{
				//Log.v("questions", i + "");
				String linkHref = "http://www.careercup.com" + link.attr("href");
				String linkText = link.text();
				if (linkText.length() > 400)
				{
					linkText = linkText.substring(0, 400);
					post.add(linkText + "....\n");

				}
				else
					post.add(linkText + "\n");

				++i;
			}
			ans = !ans;
		}

		
		post.add("\n\n\tLoad Next Page....\n\n");
		//Log.v("image " + post.size() + result.toString(), imguristr + " ");
		
		if (result == null)
		{
			Log.v("Reached display", "Load kri image!!!! "+"\n"+uri);
			try
			{
				result = new AsyncLoadImage(this, uri).execute(uri).get();
				if(result==null)
				{
					result=BitmapFactory.decodeResource(getResources(), R.drawable.company);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		lv.setAdapter(new VersionAdapter(this, post, result));
		lv.setSelectionFromTop(top, 0);
		lv.setScrollingCacheEnabled(false);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		if (position != l.getCount() - 1)
		{
			if (2 * position >= myelements.size())
				return;

			Element temp = myelements.get(position * 2);
			String linkHref = "http://www.careercup.com" + temp.attr("href");
			Intent intent = new Intent(CareercupActivity.this, DisplayAnswer.class);
			intent.putExtra("URL", linkHref);
			intent.putExtra("que", "hello");

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			result.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();

			intent.putExtra("image", byteArray);
			intent.putExtra("que", post.get(position));
			startActivity(intent);
		}

		else
		{
			Log.v("nxt page", "next page ki requirement h");
			if (currentpage == maxpages || clicked == 1)
				return;
			clicked = 1;
			setProgressBarIndeterminateVisibility(true);
			try
			{
				n = new URL(url);
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}

			++currentpage;
			Log.v("nxt page", url + "&n=" + currentpage);

			new AsyncLoadCareercup(this, url + "&n=" + currentpage, maxpages).execute(n);
		}

	}

}
