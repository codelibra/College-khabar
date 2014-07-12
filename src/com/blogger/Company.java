package com.blogger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Company extends Activity{

	TextView cname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company);
		cname= (TextView) findViewById(R.id.companyname);
		Bundle getcompany= getIntent().getExtras();
		String str=getcompany.getString("name");
		cname.setText("U pressed "+str);
	}
	

}
