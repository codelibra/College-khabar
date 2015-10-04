package com.blogger;


import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class AsyncLoadCareercup extends AsyncTask<URL, Integer, Elements> {
    private CareercupActivity cactivity;
    private String load;
    private String imagesrc;
    private int maxpages;

    public AsyncLoadCareercup(CareercupActivity temp, String url, int maxi) {
        cactivity = temp;
        load = url;
        maxpages = maxi;
    }

    @Override
    protected Elements doInBackground(URL... urls) {
        String title = "null h";
        Elements content = null;
        Log.v("titlessssssssssssssss", "hiiiiii");
        try {

            String str = urls.toString();
            long st = System.currentTimeMillis();

            Document doc = Jsoup.connect(load).userAgent("Mozilla").timeout(100000).get();

            Log.v("time taken=  ", ((st - System.currentTimeMillis()) / 1000) + " ");
            content = doc.getElementsByAttributeValueStarting("href", "/question?id=");
            if (maxpages == -1) {
                // Elements images = doc.select("img[src~=(?i)\\.(png)]");
                Elements images = doc.getElementsByTag("img");
                imagesrc = null;

                Log.v("doosre loop ka timing bhai ", "image nikaalne ko gye");
                if (images.size() > 0) {
                    for (int i = 0; i < images.size(); ++i) {
                        Element temp = images.get(i);
                        if (temp != null)
                            imagesrc = temp.attr("src");
                        if (imagesrc != null)
                            break;
                    }
                }

                Log.v("doosre loop ka timing bhai ", "image nikaal liiiii");
                int mx = 1;
                Elements content2 = doc.getElementsByClass("pagenumber");

                for (Element link : content2) {
                    if (mx == 1 && link == null) {
                        mx = 1;
                        break;
                    } else {
                        String check = link.text();
                        int k = Integer.parseInt(check);
                        if (k > mx)
                            mx = k;
                    }
                }
                maxpages = mx;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    protected void onPostExecute(Elements title) {
        //Log.v("error yaha h", maxpages + " " + title + " " + imagesrc);
        cactivity.Display(maxpages, title, imagesrc);
    }
}
