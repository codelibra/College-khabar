package com.blogger;

import android.app.Application;

import com.parse.Parse;
import com.parse.PushService;

public class RumourApplicaton extends Application {
    public void onCreate() {
        Parse.initialize(this, Constants.parseId, Constants.parsePass);
        PushService.setDefaultPushCallback(this, ExpectedActivity.class);
    }
}
