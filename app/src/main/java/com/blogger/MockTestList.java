package com.blogger;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class MockTestList extends ListActivity {

    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_list);

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


        ArrayList<String> tests = new ArrayList<String>();
        tests.add("Aptitude");
        tests.add("Verbal Ability");
        tests.add("Logical Reasoning");
        tests.add("Verbal Reasoning");
        tests.add("C Programming");
        tests.add("JAVA Programming");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MockTestList.this,
                android.R.layout.simple_list_item_1, tests);

        setListAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(
                "The Tests are of 30 mins with 20 Questions \n Marking Scheme (+3/-1)!!\n Click on List ONLY when ready to give test \n Enjy :)")
                .setTitle("Instructions:-").setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mock_test_list, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        if (isNetworkAvailable() == false) {
            noInternetAccess();
            return;
        }

        Intent intent = new Intent(this, MockTestActivity.class);
        switch (position) {
            case 0:
                intent.putExtra("url", "http://indiabix.com/online-test/aptitude-test/random");
                break;
            case 1:
                intent.putExtra("url", "http://indiabix.com/online-test/verbal-ability-test/random");
                break;
            case 2:
                intent.putExtra("url", "http://indiabix.com/online-test/logical-reasoning-test/random");
                break;
            case 3:
                intent.putExtra("url", "http://indiabix.com/online-test/verbal-reasoning-test/random");
                break;
            case 4:
                intent.putExtra("url", "http://indiabix.com/online-test/c-programming-test/random");
                break;
            case 5:
                intent.putExtra("url", "http://indiabix.com/online-test/java-programming-test/random");
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.action_leaderboard) {
            Intent intent = new Intent(this, ViewLeaderboard.class);
            startActivity(intent);
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void noInternetAccess() {
        Toast.makeText(this, "There is NO INTERNET ACCESS!!! :(", Toast.LENGTH_LONG).show();
    }

}
