package com.blogger;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.extensions.android3.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.GoogleKeyInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.blogger.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VisitedActivity extends ListActivity {

    public static final String POST_ID_KEY = "POST_ID";
    public static final String POST_ID_TITLE = "POST_ID_TITLE";
    public static final String POST_CONTENT = "POST_CONTENT";
    public static final String POST_INDEX = "POST_INDEX";
    private static final Level LOGGING_LEVEL = Level.ALL;
    private static final String TAG = "PostList";
    public static String BLOG_ID = "2012026081889905346";
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new AndroidJsonFactory();
    public String name;
    public List<String> titles;
    public Database enter = new Database(VisitedActivity.this);
    public ListView lv;
    com.google.api.services.blogger.Blogger service;
    private List<Post> posts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_post_list);

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

        ClientCredentials.errorIfNotSpecified();

        service = new com.google.api.services.blogger.Blogger.Builder(transport, jsonFactory, null)
                .setApplicationName("Google-BloggerAndroidSample/1.0")
                .setJsonHttpRequestInitializer(new GoogleKeyInitializer(ClientCredentials.KEY))
                .build();

        Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);
        setProgressBarIndeterminateVisibility(true);
        lv = getListView();

        if (isNetworkAvailable() == false) {
            enter.open();
            titles = getListfromDatabase();
            this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    titles));
            enter.close();
        } else if (titles == null && enter.isEmpty(this) == true)
            new AsyncLoadPostList(this).execute();

        else if (titles != null) {
            // disabled temporarily!!
            // this.setListAdapter(new ArrayAdapter<String>(this,
            // android.R.layout.simple_list_item_1, titles));
            lv.setAdapter(new VersionAdapterVisited(VisitedActivity.this, titles));
            lv.setScrollingCacheEnabled(false);
        } else {
            enter.open();
            titles = getListfromDatabase();
            this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    titles));
            enter.close();
            new AsyncLoadPostList(this).execute();
        }

    }

    private List<String> getListfromDatabase() {
        titles = enter.PostList();
        return titles;
    }

    public void setModel(List<Post> result) {
        setProgressBarIndeterminateVisibility(false);
        this.posts = result;
        titles = new ArrayList<String>(result.size());
        enter.open();
        for (Post post : posts) {
            titles.add(post.getTitle());
            enter.AddEntry(post.getTitle(), " ");
        }
        // changing temporarily!!!
        // this.setListAdapter(new ArrayAdapter<String>(this,
        // android.R.layout.simple_list_item_1, titles));

        lv.setAdapter(new VersionAdapterVisited(VisitedActivity.this, titles));
        lv.setScrollingCacheEnabled(false);

        Log.i("VisitedActivity", enter.getData());
        enter.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (isNetworkAvailable() == false) {
            noInternetAccess();
            return;
        }

        if (posts != null) {
            String postId = this.posts.get(position).getId().toString();
            String title = this.posts.get(position).getTitle();
            Log.v(TAG, "postId: " + postId + " selected '" + title + "'");
            enter.open();
            String contentpost = enter.getContent(title);
            if (contentpost.compareTo(" ") == 0) {
                Intent i = new Intent(getApplicationContext(), PostDisplayActivity.class);
                i.putExtra(POST_ID_KEY, postId);
                i.putExtra(POST_ID_TITLE, title);
                i.putExtra(POST_CONTENT, " ");
                i.putExtra("present", 0);
                startActivityForResult(i, 12345);
                super.onListItemClick(l, v, position, id);
            } else {
                Intent i = new Intent(getApplicationContext(), PostDisplayActivity.class);
                i.putExtra(POST_ID_KEY, postId);
                i.putExtra(POST_ID_TITLE, title);
                i.putExtra("present", 1);
                i.putExtra(POST_CONTENT, contentpost);
                startActivity(i);
                super.onListItemClick(l, v, position, id);
            }
            enter.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12345) {
            if (resultCode == RESULT_OK) {
                enter.open();
                String con = data.getStringExtra("saved");
                String tit = data.getStringExtra("title");
                enter.UpdateEntry(tit, con);
                enter.close();
            }
        }
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
