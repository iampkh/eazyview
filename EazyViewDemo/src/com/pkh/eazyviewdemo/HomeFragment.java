package com.pkh.eazyviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements OnClickListener{
	/**
	 * button object for tutoiral,source,launch view
	 */
	Button mWordPress,mGithub,mLaunchview;

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.activity_eazyview_demo, container, false);
	         mWordPress=(Button) rootView.findViewById(R.id.wordpressTuto);
	         mGithub=(Button) rootView.findViewById(R.id.githubSrc);
	         mLaunchview=(Button) rootView.findViewById(R.id.launchEazyView);
	         mWordPress.setOnClickListener(this);
	         mGithub.setOnClickListener(this);
	         mLaunchview.setOnClickListener(this);
	         
	        return rootView;
	    }

	@Override
	public void onClick(View v) {
		EazyViewDemoActivity.OnButtonSelected(v.getId());
	}
}
