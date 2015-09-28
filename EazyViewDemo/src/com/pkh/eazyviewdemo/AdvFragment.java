package com.pkh.eazyviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AdvFragment extends Fragment{
	/**
	 * button object for tutoiral,source,launch view
	 */
	Button mWordPress,mGithub,mLaunchview;
	
	private InterstitialAd interstitial;
	// Request for Ads
				AdRequest adRequest;

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.adv_frag, container, false);
	      
	        
	      //Locate the Banner Ad in activity_main.xml
			AdView adView = (AdView) rootView.findViewById(R.id.adViewAdvPge);
			
			// Request for Ads
			 adRequest = new AdRequest.Builder()
			
			// Add a test device to show Test Ads
			 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
			 .addTestDevice("")
					.build();
			
			// Load ads into Banner Ads
			adView.loadAd(adRequest);
	        
	     // Prepare the Interstitial Ad
			interstitial = new InterstitialAd(getActivity().getApplicationContext());
			// Insert the Ad Unit ID
			interstitial.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
	        
	         
	        return rootView;
	    }
	 
	 
	 @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// Load ads into Interstitial Ads
				interstitial.loadAd(adRequest);
				
				// Prepare an Interstitial Ad Listener
				interstitial.setAdListener(new AdListener() {
					public void onAdLoaded() {
						// Call displayInterstitial() function
						displayInterstitial();
					}
				});
	}
	 
	/**
	 * show big banner add
	 */
	 public void displayInterstitial() {
			// If Ads are loaded, show Interstitial else show nothing.
			if (interstitial.isLoaded()) {
				interstitial.show();
			}
		}
	
}
