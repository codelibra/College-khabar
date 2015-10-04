package com.blogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EMailActivity extends Activity {

    EditText subject, name, pbdescription, comments;
    String sub, sendername, description, txt;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        /*try
		{
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009dde")));
		}
		catch (Exception e)
		{
			Log.e("splash",e.getMessage());
		}*/

        intialize();
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                retrivestrings();
                String emailAddress[] = {"shivirocks2909@gmail.com"};
                String message = "Hi sir i am " + sendername + "\nProblem :\n" + description + "\n" + txt;
                Intent emailsend = new Intent(android.content.Intent.ACTION_SEND);
                emailsend.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddress);
                emailsend.putExtra(android.content.Intent.EXTRA_SUBJECT, sub);
                emailsend.setType("plain/text");
                emailsend.putExtra(android.content.Intent.EXTRA_TEXT, message);
                startActivity(emailsend);
            }
        });
    }


    protected void retrivestrings() {
        // TODO Auto-generated method stub
        sub = subject.getText().toString();
        sendername = name.getText().toString();
        description = pbdescription.getText().toString();
        txt = comments.getText().toString();
    }


    private void intialize() {
        subject = (EditText) findViewById(R.id.EMeditText4);
        name = (EditText) findViewById(R.id.EMeditText1);
        pbdescription = (EditText) findViewById(R.id.EMeditText2);
        comments = (EditText) findViewById(R.id.EMeditText3);
        submit = (Button) findViewById(R.id.send);
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
