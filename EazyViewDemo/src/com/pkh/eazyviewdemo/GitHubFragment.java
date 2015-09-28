package com.pkh.eazyviewdemo;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class GitHubFragment extends Fragment {

	/**
	 * link for github
	 */
	public static final String Github_src="https://github.com/iampkh/eazyview";
	/**
	 * instance for webview
	 */
	WebView mWebView;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.webview_frag, container, false);
	        /**
	         * adding advertisements
	         */
	        addAdvertisements(rootView);
	        mWebView=(WebView) rootView.findViewById(R.id.webView1);
	        mWebView.loadUrl(Github_src);
	        return rootView;
	    }
	 
	 
	 /**
	  * advertisment enabling
	  * @param parentView
	  */
	 private void addAdvertisements(View parentView) {
		//Locate the Banner Ad in activity_main.xml
			AdView adView = (AdView) parentView.findViewById(R.id.adViewSrcPge);
			
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
