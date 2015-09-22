package com.pkh.eazyviewdemo;

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
	        mWebView=(WebView) rootView.findViewById(R.id.webView1);
	        mWebView.loadUrl(Github_src);
	        return rootView;
	    }
}
