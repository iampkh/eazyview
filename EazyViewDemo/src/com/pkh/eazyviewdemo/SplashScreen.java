package com.pkh.eazyviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class SplashScreen extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		Handler mHandler=new Handler();
		Runnable mRunnable=new Runnable() {
			
			@Override
			public void run() {
				/**
				 * initating the screen (andengine CAMERA width and height)
				 */
				
				Intent intent=new Intent(getApplicationContext(),EazyViewDemoActivity.class);
				startActivity(intent);
				
				
				finish();
				
				
			}
		};
		
		mHandler.postDelayed(mRunnable, 5000);
	}


}
