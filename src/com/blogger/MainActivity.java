package com.blogger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.PushService;

public class MainActivity extends Activity
{
	public AutoCompleteTextView textView;
	public Button btn,mock;
	public String url;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_main);
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if (currentUser == null)
			navigateToLogin();
		else
			Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_LONG).show();
		
		
		PushService.subscribe(this, "companies", ExpectedActivity.class);
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
			
		
		
		Button visitedbutton = (Button) findViewById(R.id.leaderboardbutton1);
		visitedbutton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, VisitedActivity.class);
				startActivity(intent);
			}
		});

		Button expectedbutton = (Button) findViewById(R.id.button2);
		expectedbutton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, ExpectedActivity.class);
				startActivity(intent);
			}
		});

		Button rumourbutton = (Button) findViewById(R.id.button3);
		rumourbutton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, RumourActivity.class);
				startActivity(intent);
			}
		});
		
		mock=(Button) findViewById(R.id.mocktest);
		mock.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				if(isNetworkAvailable()==false)
				{
					noInternetAccess();
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, MockTestList.class);
				startActivity(intent);
			}
		});

		
		
		final String[] companies = new String[] {

		"A9", "Abs india pvt. ltd.", "Accenture", "Achieve Internet", "Adap.tv", "Adobe", "ADP",
				"Advisory Board Company", "Agilent Technologies", "Akamai", "Alcatel Lucent",
				"Altera", "Amazon", "AMD", "Amdocs", "American Airlines", "Apache Design", "Apple",
				"AppNexus", "Argus", "Aricent", "Arista Networks", "Aristocrat Gaming", "Ask.com",
				"Aspire Systems", "ASU", "Athena Health", "Automated Traders Desk",
				"Autonomy Zantaz", "Axiom Sources", "Baidu", "Bank of America", "Barclays Capital",
				"bazaarvoice", "big fish", "bloomberg lp", "blue jeans", "bocada", "boeing",
				"booking.com", "brainware", "bt", "cadence inc", "capgemini", "capitaliq",
				"careercup", "caritor", "cavium networks", "ccn", "cdac-acts", "cgi-ams",
				"chegg.com ", "chelsio communications", "chicago mercantile exchange", "chronus",
				"cisco systems", "citigroup", "citrix online", "citrix system inc", "cloudera",
				"cloudmere, inc.", "cmc ltd", "cognzant technology solutions", "collective",
				"computer associates", "continental", "credit suisse", "crompton greeves", "csc",
				"csr", "cubic transportation systems limited", "daptiv",
				"defense research and development organization of india",
				"deloitte consulting llp", "delve networks", "denmin group", "deshaw inc",
				"did-it.com", "directi", "dover organization", "ebay", "efi", "egenera",
				"electronic arts", "emc", "epic systems", "epsilon", "ericsson",
				"eterno infotech pvt ltd", "expedia", "ez prints", "f5 networks", "fabrix",
				"facebook", "factset research systems,inc", "fair issac", "fiorano", "flextrade",
				"flipkart", "fortinet", "future advisor", "future group,mumbai", "fynanz",
				"gartner", "gayatri vidya parishad", "ge(general electric)", "georgia pacific",
				"global scholar", "globaltech research", "gluster", "godaddy", "gold-tier",
				"goldman sachs", "google", "green bricks", "groupon", "grubhub", "guruji",
				"harman kadron", "hcl", "hcl america", "headrun technologies pvt ltd",
				"hewlett packard", "hi5", "highbridge capital", "home depot", "honeywell",
				"huawei", "hunan asset", "ibibo", "ibm", "iconologic", "igate", "iit-d", "ilabs",
				"imagination technologies", "img", "infibeam", "infinium", "informatica",
				"infosys", "initto", "inmobi", "intel", "interactive brokers",
				"interactive design", "internet question", "interrait", "intuit", "ion idea",
				"ion trading", "ipowerfour", "iron mountain", "ittiam systems", "ivycomptech",
				"izon", "jane street", "jda", "jp morgan", "juniper networks", "kalido", "kaseya",
				"kerkestavni computecha corporatci", "kingfisher", "kingfisher airlines", "kiwox",
				"knewton", "knoa software", "knowledge systems", "knowledgebase", "komli media",
				"kpro solutions", "lab126", "labs247", "laserfiche", "lexisnexis", "lime labs",
				"linkedin", "literaturebay.com", "live nation", "liveramp", "lockheed martin",
				"london investment bank", "lsi", "lunatic server solutions", "magma",
				"manhattan associates", "maq", "marketrx", "marvell", "mathworks", "mcafee",
				"medio systems", "megasoft", "mentor graphics", "micron", "microsoft",
				"microstrategy", "mih", "mindtree wireless india", "model n", "monitor group",
				"morgan stanley", "motorola", "mounzit", "moyer group", "mspot", "myntra",
				"myntra.com", "national informatics centre", "national instruments", "ness",
				"netapp", "netflix", "nexabion", "nextag", "nisum technologies",
				"nivio technologies", "nomura", "novell", "nvidia", "one97", "onmobile", "oracle",
				"overstock.com", "palantir technology", "paypal", "pega", "persistent systems",
				"philips", "pinterest", "pj pvt ltd", "pocketgems", "prds", "progress", "qnx",
				"qsi healthcare", "qualcomm", "rapleaf", "raytheon", "real networks",
				"rebellion research", "relq software company limited", "research in motion",
				"ricoh", "rightc", "riverbed", "roamware", "rovicorp", "roxar", "rsa",
				"sabre holdings", "safenet", "sage software", "salesforce", "samsung", "sap labs",
				"sapient corporation", "sas research", "schneider electric", "search media",
				"sears holding", "shutterfly", "siemens", "sig(susquehanna international group)",
				"signpost", "sileria inc.", "singapore technologies", "socialcam", "softchoice",
				"sonoa systems", "sophos", "spotify", "srmicro info systems", "starent networks",
				"startup", "student", "sungard", "swapton solutions", "symantec",
				"symphony services", "syncfusion", "synopsys r&d", "take two interactive",
				"tally solutions", "tata consultancy services", "tech india line", "techlogix",
				"tejas networks", "tellabs", "texas instruments", "the digital group",
				"the royal bank of scotland chennai", "theplatform", "thirdware technologies",
				"thomson reuters", "thoughtworks", "tp", "trg", "tribal fusion", "trilogy",
				"turing software", "twitter", "two sigma", "united healthgroup", "unknown",
				"urban touch", "usaa", "usinternetworking", "vans", "vanu", "vdopia",
				"video gaming technologies", "vizury", "vmware inc", "walmart labs", "watchguard",
				"wichorus", "wipro technologies", "wireless generation", "worksapp", "xurmo",
				"yahoo", "yatra.com", "yelp", "zillow", "zoosk", "zs associates", "zycus", "zynga" };

		btn = (Button) findViewById(R.id.searchcompany);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, companies);
		textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		textView.setAdapter(adapter);

		btn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(btn.getWindowToken(), 0);
				
				if(isNetworkAvailable()==false)
				{
					noInternetAccess();
					return;
				}
				
				String text = textView.getText().toString();
				if(text ==null)
				{
					Toast.makeText(MainActivity.this, "Please select from autocomplete", Toast.LENGTH_LONG).show();
					return;
				}
				
				boolean found=false;
				for(int i=0;i<companies.length;++i)
				{
					if(text.contentEquals(companies[i])==true)
					{
						found=true;
						break;
					}
				}
				
				if(found==false)
				{
					Toast.makeText(MainActivity.this, "Please select from autocomplete", Toast.LENGTH_LONG).show();
					return;
				}
				text = CleanString(text);
				
				
				url = "http://www.careercup.com/page?pid=" + text;

				Intent intent = new Intent(MainActivity.this, CareercupActivity.class);
				intent.putExtra("company", url);
				startActivity(intent);
			}

			private String CleanString(String text)
			{
				String ans = "", ans2 = "";
				ans = text.toLowerCase();
				ans = ans.replace(' ', '-');	

				for (int i = 0; i < ans.length(); ++i)
				{
					if (ans.charAt(i) != '.' && ans.charAt(i) != ',')
						ans2 += ans.charAt(i);
				}
				ans2 += "-interview-questions";
				return ans2;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		MenuInflater blow = getMenuInflater();
		blow.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case R.id.about:
			Intent i = new Intent("com.manit.placements.ABOUT");
			startActivity(i);
			break;

		case R.id.exit:
			finish();
			break;
		
		case R.id.devmail:
			Intent intent = new Intent(MainActivity.this, EMailActivity.class);
			startActivity(intent);
			break;
			
		case R.id.action_profile:
			Intent intent2 = new Intent(MainActivity.this, ProfileActivity.class);
			startActivity(intent2);
			break;
			
		}
		return true;

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
		finish();
	}
	
	private void navigateToLogin()
	{
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}



}
