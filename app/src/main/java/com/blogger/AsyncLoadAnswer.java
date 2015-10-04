package com.blogger;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author panicker
 */

public class AsyncLoadAnswer extends AsyncTask<String, Void, Elements> {
    public String load;
    public DisplayAnswer temp;

    public AsyncLoadAnswer(DisplayAnswer in, String p) {
        temp = in;
        load = p;
    }

    @Override
    protected Elements doInBackground(String... url) {
        Elements content = null;
        Elements content2 = null;
        Element k;

        Log.v("heloooooo!!!!!!!!!!", load);
        try {
            Document doc = Jsoup
                    .connect(load)
                    .timeout(100000).get();
            content = doc.getElementsByClass("comment");
            for (Element link : content) {
                link = link.child(1);
                String linkText = link.text();
                Log.v("text", linkText);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
    }

    @Override
    protected void onPostExecute(Elements title) {
        temp.Display(title);
    }


}
