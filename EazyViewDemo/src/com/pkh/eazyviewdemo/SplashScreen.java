package com.pkh.eazyviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class SplashScreen extends ActionBarActivity {
	ImageView pView,kView,hView,appsView,balanceView;
	
	Rect pViewRect = new Rect();
	Rect globalRect=new Rect();
	Point globalOffset = new Point();
	View container;
	
	Handler mHandler;
	Runnable mRunnable;
	
	MediaPlayer m = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		container = findViewById(R.id.container);
		
		pView = (ImageView) findViewById(R.id.pview);
		kView = (ImageView) findViewById(R.id.kview);
		hView = (ImageView) findViewById(R.id.hview);
		appsView = (ImageView) findViewById(R.id.appview);
		balanceView = (ImageView) findViewById(R.id.balanceview);
		
		/**
		 * initiating media player
		 */
		m = new MediaPlayer();
		

		Log.e(Utility.TAG, "OnResume:-"+pViewRect.left+"_WW"+pViewRect.width());
		
		/**
		 * delaying 
		 */
		 mHandler=new Handler();
		 mRunnable=new Runnable() {
			
			@Override
			public void run() {
				/**
				 * initating the screen (andengine CAMERA width and height)
				 */
				
				Intent intent=new Intent(getApplicationContext(),EazyViewDemoActivity.class);
				startActivity(intent);
				
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				
				
			}
		};
		
	
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(m!=null && m.isPlaying()){
			 m.stop();
	         m.release();
	         m=null;
		}
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		/*
		 * create and start animating the logo
		 * 
		 */
		createAnimation();
		
		Log.e(Utility.TAG, "OnResume:-"+pViewRect.left+"_WW"+pViewRect.width());
	}
	
	
	private void playAnimSong() {
		 try {
		        if (m!=null && m.isPlaying()) {
		            m.stop();
		            m.release();
		            m = new MediaPlayer();
		        }else{
		        	m = new MediaPlayer();
		        }

		        AssetFileDescriptor descriptor = getAssets().openFd("bg_music.mp3");
		        m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
		        descriptor.close();

		        m.prepare();
		        m.setVolume(2f, 2f);
		        m.setLooping(false);
		        m.start();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

	}
	private void createAnimation() {
		WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
		Display display =windowManager.getDefaultDisplay();
		Point screenPoint=getDisplaySize(display);
		int height=screenPoint.y;
		int width=screenPoint.x;
		/**
		 * creating animation for Pimage
		 */
		Animation translatePView
		  = new TranslateAnimation(-(width/2), 0, 0, 0);
		translatePView.setDuration(1300);
		translatePView.setInterpolator(new BounceInterpolator());
		translatePView.start();
		pView.setAnimation(translatePView);
		/**
		 * creating animation for Himage
		 */
		Animation translateHview
		  = new TranslateAnimation(width+(width/2), 0, 0, 0);
		translateHview.setDuration(1300);
		translateHview.setInterpolator(new BounceInterpolator());
		translateHview.start();
		hView.setAnimation(translateHview);
		
		/**
		 * creating animation for Appsimage
		 */
		Animation translateAppsview
		  = new TranslateAnimation(width+(width/2), 0, 0, 0);
		translateAppsview.setDuration(1000);
		translateAppsview.setInterpolator(new AnticipateInterpolator());
		translateAppsview.start();
		appsView.setAnimation(translateAppsview);
		//pView.startAnimation(translate);
		
		/**
		 * creating animation for K image
		 */
		Animation translateKview
		  = new TranslateAnimation(0, 0, height+(height/2), 0);
		translateKview.setDuration(900);
		translateKview.setInterpolator(new LinearInterpolator());
		translateKview.start();
		kView.setAnimation(translateKview);
		
		/**
		 * creating animation for balance image
		 */
		Animation translateBlnceview
		  = new TranslateAnimation(0, 0, -(height/2), 0);
		translateBlnceview.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				playAnimSong();
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				mHandler.postDelayed(mRunnable, 500);
				
			}
		});

		translateBlnceview.setDuration(1500);
		translateBlnceview.setInterpolator(new BounceInterpolator());
		translateBlnceview.start();
		balanceView.setAnimation(translateBlnceview);
		//pView.startAnimation(translate);
		
		

	}
	
	public static Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}

}
