package com.blogger;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpNew extends ListActivity
{
	public String usernamenw, yearnw, emailnw, passwordnw, branchnw;
	public RadioButton cse,ece,mech,civil,ele,archi,meta;
	public RadioGroup rg;
	public Button btn;
	public String[] signup;
	public ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_sign_up_new);
		/*try
		{
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
		}
		catch (Exception e)
		{
			Log.e("splash",e.getMessage());
		}*/

		signup = new String[] { "Username", "Password", "Email & Year Of Passout",
				"Branch" };
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, signup);
		
		setListAdapter(adapter);
		
		btn=(Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				jstsignup();
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		switch (position)
		{
		case 0:

			LayoutInflater li = LayoutInflater.from(this);
			View promptsView = li.inflate(R.layout.username_signup, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setView(promptsView);
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			
			final EditText userInput = (EditText) promptsView.findViewById(R.id.username_input);

			alertDialogBuilder.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							usernamenw = userInput.getText().toString();
							signup[0]=usernamenw;
							adapter.notifyDataSetChanged();
							
							Log.v("dekhte h",usernamenw+ "lol" );
							((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
						    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.cancel();
							((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
						    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
						}
					});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

			break;

		case 1:

			LayoutInflater li2 = LayoutInflater.from(this);
			View promptsView2 = li2.inflate(R.layout.password_signup, null);

			AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
			alertDialogBuilder2.setView(promptsView2);
			InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm2.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			final EditText userInput2 = (EditText) promptsView2.findViewById(R.id.password_input);

			alertDialogBuilder2.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							passwordnw = userInput2.getText().toString();
							((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
						    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
							
							signup[1]="*****";
							adapter.notifyDataSetChanged();
		
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.cancel();
							((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
						    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
						}
					});

			AlertDialog alertDialog2 = alertDialogBuilder2.create();
			alertDialog2.show();

			break;

		case 2:
			LayoutInflater li3 = LayoutInflater.from(this);
			View promptsView3 = li3.inflate(R.layout.yearpassout_signup, null);

			AlertDialog.Builder alertDialogBuilder3 = new AlertDialog.Builder(this);
			alertDialogBuilder3.setView(promptsView3);
			InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm3.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			final EditText userInput3 = (EditText) promptsView3.findViewById(R.id.email_input);
			final EditText userInput4 = (EditText) promptsView3
					.findViewById(R.id.yearpassout_input);
			
			alertDialogBuilder3.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							emailnw = userInput3.getText().toString();
							yearnw = userInput4.getText().toString();
							((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
						    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
							
							signup[2]=emailnw+"\n"+yearnw;
							adapter.notifyDataSetChanged();
		
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.cancel();
							((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
						    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
						}
					});

			AlertDialog alertDialog3 = alertDialogBuilder3.create();
			alertDialog3.show();

			break;

		case 3:
			LayoutInflater li4 = LayoutInflater.from(this);
			View promptsView4 = li4.inflate(R.layout.branch_signup, null);

			AlertDialog.Builder alertDialogBuilder4 = new AlertDialog.Builder(this);
			alertDialogBuilder4.setView(promptsView4);
			final RadioGroup rg=(RadioGroup) promptsView4.findViewById(R.id.branchradio);
			alertDialogBuilder4.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							int selectedid=rg.getCheckedRadioButtonId();
							switch(selectedid)
							{
							case R.id.rb1:
								branchnw = "CSE";
								break;
							case R.id.rb2:
								branchnw = "ECE";
								break;
							case R.id.rb3:
								branchnw = "MECH";
								break;
							case R.id.rb4:
								branchnw = "CIVIL";
								break;
							case R.id.rb5:
								branchnw = "CIVIL";
								break;
							case R.id.rb6:
								branchnw = "ARCHI";
								break;
							case R.id.rb7:
								branchnw = "META";
								break;
							}
							signup[3]=branchnw;
							adapter.notifyDataSetChanged();
		
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.cancel();

						}
					});

			AlertDialog alertDialog4 = alertDialogBuilder4.create();
			alertDialog4.show();

			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up_new, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if(item.getItemId()==R.id.action_sign_up)
		{
			jstsignup();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void jstsignup()
	{
		if (usernamenw == null || passwordnw == null || emailnw == null || yearnw == null
				|| branchnw == null)
		{
			Toast.makeText(SignUpNew.this, "All entries not filled!! Please Check!  :( ",
					Toast.LENGTH_LONG).show();
			return;
		}
		setProgressBarIndeterminateVisibility(true);
		ParseUser user = new ParseUser();
		user.setUsername(usernamenw);
		user.setPassword(passwordnw);
		user.setEmail(emailnw);
		user.put("year", yearnw);
		user.put("branch", branchnw);
		user.put(ParseConstants.KEY_SCORE, 0);
		user.put(ParseConstants.KEY_TESTS, 0);
		setProgressBarIndeterminateVisibility(true);
		user.signUpInBackground(new SignUpCallback()
		{

			@Override
			public void done(ParseException e)
			{
				setProgressBarIndeterminateVisibility(false);

				if (e == null)
				{
					Toast.makeText(SignUpNew.this, "saved in background",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(SignUpNew.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
				}
				else
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							SignUpNew.this);
					builder.setMessage(e.getMessage()).setTitle("Error!!!")
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});

	}

}
