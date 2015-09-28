package com.pkh.eazyviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class WordPressFragment extends Fragment {
	public static final String TUT_PART1="https://eazyviewtutorial.wordpress.com/2015/06/24/how-to-use-eazyview-library-in-a-androidproject/";
	public static final String TUT_PART2="https://eazyviewtutorial.wordpress.com/2015/06/24/how-to-use-eazyview-library-in-a-androidproject-part2/";
	/**
	 * instance for webview
	 */
	WebView mWebView;
	/**
	 * Button for swithcing the turoial
	 */
	Button mPartSwitchButton;
	/*
	 * flag to swithc part 1 or part 2
	 */
	boolean isPart1Showing=true;
	/**
	 * stirng for part 1 & part 2
	 */
	public static final String PART1="Part 1";
	public static final String PART2="Part 2";
	

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.webview_frag_wordpress, container, false);
	        /**
	         * adding advertisements
	         */
	        addAdvertisements(rootView);
	        
	         mWebView=(WebView) rootView.findViewById(R.id.webView1);
	         mPartSwitchButton=(Button) rootView.findViewById(R.id.partSwitcher);
	         mPartSwitchButton.setText(PART2);
			 mWebView.loadUrl(TUT_PART1);
	         isPart1Showing=true;
	         
	         mPartSwitchButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(isPart1Showing){
						mPartSwitchButton.setText(PART1);
						mWebView.loadUrl(TUT_PART2);
						isPart1Showing=false;
					}else{
						mPartSwitchButton.setText(PART2);
						mWebView.loadUrl(TUT_PART1);
						isPart1Showing=true;
					}
					
				}
			});
	         
	        return rootView;
	    }
	 
	 /**
	  * advertisment enabling
	  * @param parentView
	  */
	 private void addAdvertisements(View parentView) {
		//Locate the Banner Ad in activity_main.xml
			AdView adView = (AdView) parentView.findViewById(R.id.adViewTutPge);
			
			// Request for Ads
			AdRequest adRequest = new AdRequest.Builder()
			
			// Add a test device to show Test Ads
			 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
			 .addTestDevice("")
					.build();
			
			// Load ads into Banner Ads
			adView.loadAd(adRequest);

	}
	 
}
