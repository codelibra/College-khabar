package com.blogger;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends Activity {

    MediaPlayer clip;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

	/*	try
        {
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
			
		}
		catch (Exception e)
		{
			Log.e("splash",e.getMessage());
		}
	*/


        clip = MediaPlayer.create(Splash.this, R.raw.splashsound);
        clip.start();
        Thread timer = new Thread() {

            public void run() {

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent splashtry = new Intent(Splash.this, MainActivity.class);
                    startActivity(splashtry);
                }

            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        clip.release();
        finish();
    }

}
