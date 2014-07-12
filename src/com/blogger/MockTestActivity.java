		package com.blogger;
		
		import java.net.URL;
		
		import org.jsoup.nodes.Element;
		import org.jsoup.select.Elements;
		
		import com.parse.ParseUser;
		
		import android.app.ActionBar;
		import android.app.Activity;
		import android.app.AlertDialog;
		import android.app.Dialog;
		import android.app.ProgressDialog;
		import android.content.Intent;
		import android.graphics.Color;
		import android.graphics.drawable.ColorDrawable;
		import android.os.Bundle;
		import android.os.CountDownTimer;
		import android.util.Log;
		import android.view.Menu;
		import android.view.MenuItem;
		import android.view.View;
		import android.view.Window;
		import android.widget.Button;
		import android.widget.ProgressBar;
		import android.widget.RadioButton;
		import android.widget.RadioGroup;
		import android.widget.TextView;
		import android.widget.Toast;
		
		public class MockTestActivity extends Activity
		{
			public Elements content;
			public int question;
			public TextView tv, sv, timer;
			public RadioButton op1, op2, op3, op4;
			public Elements temp1, temp2, temp3, temp4;
			public Element temp;
			public RadioGroup rg;
			public int correct, skipped;
			private int marks_obtained;
			public String directions;
		
			@Override
			protected void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
				setContentView(R.layout.activity_mock_new);
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
				setProgressBarIndeterminateVisibility(true);
		
				Initialise();
				correct = 0;
				skipped = 0;
				question = 1;
				Log.e("hiiii", "aya h yaha");
		
				Intent intent = getIntent();
				String load = intent.getStringExtra("url");
				URL k = null;
		
				new AsyncMockTestLoader(this, load).execute(k);
		
			}
		
			@Override
			public boolean onCreateOptionsMenu(Menu menu)
			{
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.mock_test, menu);
				return true;
			}
		
			@Override
			public boolean onMenuItemSelected(int featureId, MenuItem item)
			{
				if (item.getItemId() == R.id.action_next)
				{
					if (op1.isChecked() == false && op2.isChecked() == false && op3.isChecked() == false
							&& op4.isChecked() == false)
					{
						++skipped;
						Toast.makeText(MockTestActivity.this, "Skipped!!! :o", Toast.LENGTH_SHORT).show();
					}
					else if (temp4.text().equalsIgnoreCase("A") == true && op1.isChecked() == true)
					{
						Toast.makeText(MockTestActivity.this, "Correct!!! :)", Toast.LENGTH_SHORT).show();
						++correct;
					}
					else if (temp4.text().equalsIgnoreCase("B") == true && op2.isChecked() == true)
					{
						Toast.makeText(MockTestActivity.this, "Correct!!! :)", Toast.LENGTH_SHORT).show();
						++correct;
					}
					else if (temp4.text().equalsIgnoreCase("C") == true && op3.isChecked() == true)
					{
						Toast.makeText(MockTestActivity.this, "Correct!!! :)", Toast.LENGTH_SHORT).show();
						++correct;
					}
					else if (temp4.text().equalsIgnoreCase("D") == true && op4.isChecked() == true)
					{
						Toast.makeText(MockTestActivity.this, "Correct!!! :)", Toast.LENGTH_SHORT).show();
						++correct;
					}
		
					else
					{
						Toast.makeText(MockTestActivity.this, "WRONG ANSWER!!! :(", Toast.LENGTH_SHORT)
								.show();
					}
		
					if (question <= 19)
						next_Question(question++);
		
					else
					{
						publishresults();
					}
		
				}
				else if(item.getItemId() ==R.id.action_direction)
				{
					if (directions!=null &&  directions.length() > 0)
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
						builder.setMessage(directions).setTitle("Directions:-")
								.setPositiveButton(android.R.string.ok, null);
						AlertDialog dialog = builder.create();
						dialog.show();
					}
					else
						Toast.makeText(MockTestActivity.this, "No Special Directions..", Toast.LENGTH_SHORT)
						.show();
		
				}
				return super.onMenuItemSelected(featureId, item);
			}
		
			public void Display(Elements test)
			{
				setProgressBarIndeterminateVisibility(false);
				content = test;
				next_Question(0);
				new CountDownTimer(1800000, 1000)
				{
		
					public void onTick(long millisUntilFinished)
					{
						timer.setText("seconds remaining: " + millisUntilFinished / 1000);
					}
		
					public void onFinish()
					{
						publishresults();
					}
				}.start();
		
			}
		
			@Override
			public void onBackPressed()
			{
				publishresults();
			}
		
			private void Initialise()
			{
				rg = (RadioGroup) findViewById(R.id.radioGroup1);
				tv = (TextView) findViewById(R.id.name);
				sv = (TextView) findViewById(R.id.textView2);
				op1 = (RadioButton) findViewById(R.id.radioButton1);
				op2 = (RadioButton) findViewById(R.id.radioButton2);
				op3 = (RadioButton) findViewById(R.id.radioButton3);
				op4 = (RadioButton) findViewById(R.id.radioButton4);
				timer = (TextView) findViewById(R.id.timer);
			}
		
			public void next_Question(int index)
			{
				temp = content.get(index);
				op1.setChecked(false);
				op2.setChecked(false);
				op3.setChecked(false);
				op4.setChecked(false);
				directions=null;
				temp1 = temp.getElementsByClass("bix-td-direction");
				temp2 = temp.getElementsByClass("bix-td-qtxt");
				temp3 = temp.getElementsByClass("bix-inner-td-option");
				temp4 = temp.getElementsByClass("ib-dgray");
		
				if (temp1.text().length() != 0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					directions = temp1.text();
					builder.setMessage(temp1.text()).setTitle("Directions:-")
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
		
				tv.setText("Question:- " + question + "\n" + temp2.text());
				if (temp3.size() > 0)
					op1.setText(temp3.get(0).text());
				if (temp3.size() > 1)
					op2.setText(temp3.get(1).text());
				if (temp3.size() > 2)
					op3.setText(temp3.get(2).text());
				if (temp3.size() > 3)
					op4.setText(temp3.get(3).text());
				Log.v("Test Answer " + question, temp4.text());
		
			}
		
			@Override
			protected void onDestroy()
			{
		
				super.onDestroy();
			}
		
			public void publishresults()
			{
				marks_obtained = ((correct * 3) - (20 - correct));
				ParseUser user = ParseUser.getCurrentUser();
				int a = (Integer) user.get(ParseConstants.KEY_SCORE), b = (Integer) user
						.get(ParseConstants.KEY_TESTS);
				int one = a + marks_obtained, two = b + 1;
				user.put(ParseConstants.KEY_SCORE, one);
				user.put(ParseConstants.KEY_TESTS, two);
				user.saveEventually();
		
				final Dialog dialog = new Dialog(MockTestActivity.this, R.style.Theme_Dialog);
				dialog.setContentView(R.layout.rumour_custom_alert);
				final TextView userInput3 = (TextView) dialog.findViewById(R.id.rumour_custom_et);
				final TextView sender = (TextView) dialog.findViewById(R.id.sender_custom_et);
		
				final Button ok = (Button) dialog.findViewById(R.id.btn_ok);
		
				sender.setText("Results");
				userInput3.setText("Correct= " + correct + "\nIncorrect= " + ((20 - correct) - skipped)
						+ "\nScore Obtained= " + ((correct * 3) - (20 - correct) + skipped));
				dialog.show();
				ok.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
						Intent intent = new Intent(MockTestActivity.this, ViewLeaderboard.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
					}
				});
		
			}
		
		}
