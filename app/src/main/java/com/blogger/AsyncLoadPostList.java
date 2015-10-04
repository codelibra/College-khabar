package com.blogger;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.services.blogger.model.Post;
import com.google.api.services.blogger.model.PostList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsyncLoadPostList extends AsyncTask<Void, Void, List<Post>> {

    private static final String TAG = "AsyncLoadPostList";

    private final VisitedActivity visitedactivity;
    private final ProgressDialog dialog;
    private com.google.api.services.blogger.Blogger service;

    AsyncLoadPostList(VisitedActivity temp) {
        this.visitedactivity = temp;
        service = temp.service;
        dialog = new ProgressDialog(temp);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected List<Post> doInBackground(Void... arg0) {

        try {
            List<Post> result = new ArrayList<Post>();
            com.google.api.services.blogger.Blogger.Posts.List postsListAction = service.posts()
                    .list(VisitedActivity.BLOG_ID).setFields("items(id,title),nextPageToken");
            PostList posts = postsListAction.execute();

            // Retrieve up to five pages of results.
            int page = 1;

            while (posts.getItems() != null && page < 50) {
                page++;
                result.addAll(posts.getItems());
                String pageToken = posts.getNextPageToken();
                if (pageToken == null) {
                    break;
                }
                postsListAction.setPageToken(pageToken);
                posts = postsListAction.execute();
            }
            return result;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Post> result) {
        dialog.dismiss();
        visitedactivity.setModel(result);
    }
}

