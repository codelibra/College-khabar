package com.blogger;

import android.app.Application;

import com.parse.Parse;
import com.parse.PushService;

public class RumourApplicaton extends Application
{
	public void onCreate()
	{
		Parse.initialize(this, "384mpmBN3fxAJRBHB5MR0IMDaA2CQbwCoOneIxYZ",	"UOeWtakFjnUMerfNH5SOH69kaXJGxZv6QF36uCMf");
		PushService.setDefaultPushCallback(this, ExpectedActivity.class);
	}
}
