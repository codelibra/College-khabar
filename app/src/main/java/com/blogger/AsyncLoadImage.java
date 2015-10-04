package com.blogger;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

public class AsyncLoadImage extends AsyncTask<String, Void, Bitmap> {
    public String url;
    public CareercupActivity adp;

    AsyncLoadImage(CareercupActivity va, String img) {
        url = img;
        adp = va;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        Bitmap mIcon11 = null;

        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            if (mIcon11 == null)
                Log.v("arey yr null h image!!!", "null aai image behnchod!!");
        } catch (Exception e) {
            Log.e("Error aaa gai bhnchd", e.getMessage() + " ");
            e.printStackTrace();
            return null;
        }
        return mIcon11;
    }


}
