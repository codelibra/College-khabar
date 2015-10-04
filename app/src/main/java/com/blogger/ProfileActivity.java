package com.blogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class ProfileActivity extends Activity {

    public TextView username, email, branch, number, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser == null)
            navigateToLogin();
        else
            Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_LONG).show();

        Initialise();
        username.setText(currentUser.get(ParseConstants.KEY_USERNAME).toString().toUpperCase());
        email.setText(currentUser.get("email").toString());
        ;
        branch.setText(currentUser.get(ParseConstants.KEY_BRANCH).toString());
        number.setText(currentUser.get(ParseConstants.KEY_TESTS).toString());
        score.setText(currentUser.get(ParseConstants.KEY_SCORE).toString());
    }

    private void Initialise() {
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        branch = (TextView) findViewById(R.id.branch);
        number = (TextView) findViewById(R.id.number_of_tests);
        score = (TextView) findViewById(R.id.score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            navigateToLogin();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
