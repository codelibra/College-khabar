package com.blogger;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ActionBar;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayAnswer extends ListActivity
{
	public Elements myelements;
	public ListView lv;
	public Bitmap res;
	public TextView que;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_display_answer);
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
		que=(TextView) findViewById(R.id.question);
	
		
		lv=getListView();
		Bundle extras = getIntent().getExtras();
		String url = extras.getString("URL");
		String q=extras.getString("que");
		
		byte[] byteArray = getIntent().getByteArrayExtra("image");
		res = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		
		que.setText(q);
		setProgressBarIndeterminateVisibility(true);
		new AsyncLoadAnswer(this, url).execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.display_answer, menu);
		return true;
	}

	public void Display(Elements content)
	{
		setProgressBarIndeterminateVisibility(false);
		ArrayList<String> post = new ArrayList<String>();
		ArrayList<String> extra = new ArrayList<String>();
		ArrayList<String> writer = new ArrayList<String>();
		
		myelements = content;
		if (content == null)
		{
			Log.v("arey yr", "kuch ni  aya!!");
			return;
		}

		int j=1;
		
		for (Element link : content)
		{
			Elements code=link.getElementsByTag("code");
			Elements para=link.getElementsByTag("p");
			
			String program=code.text();
			String matter=para.text();
			
	//		Log.v("text",matter+" ");
	//		Log.v("code",program+" ");
			
			String linkHref = "http://www.careercup.com" + link.attr("href");
			
			String Text = matter+"\n\n"+program;
			String linkText=link.text();
			String votes =linkText.substring(0, 12);
			
			
			int i=linkText.length()-1;
			String authour="";
			for(;i>=0;--i)	if(linkText.charAt(i)=='-')break;
			authour=linkText.substring(i);
			int f=authour.indexOf("on", 0);
			authour=authour.substring(1, f);
			linkText=linkText.substring(12,i);
			
			writer.add(authour);
			extra.add(" "+votes.charAt(0)+votes.charAt(1));
			post.add(Text);
			++j;
		}
		
		lv.setAdapter(new VersionAdapterAnswer(this,
				this, post.size(), post,extra,writer, res));
		lv.setScrollingCacheEnabled(false);
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		finish();
	}
}
