package com.pkh.eazyview.lazyviewreplica;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class AddViewToLayout {
	private Context mContext;
	private Activity mActivity;
	private EazyView mEazyView;
	Button mLaucherBtn=null;
	FrameLayout parentLayout;
	int openDrawable=android.R.drawable.btn_star_big_on;
	int closeDrawable=android.R.drawable.btn_star_big_off;
	/**
	 * 
	 * @param context get app context
	 */
	public AddViewToLayout(Activity activity,EazyView eazyView) {
		mContext=activity.getApplicationContext();
		mActivity=activity;
		mEazyView=eazyView;
		
		mLaucherBtn = new Button(mContext);
		
		
		
	}
	public Button addLayout(int openBtnDrawableId,int closeBtnDrawableId) {

		openDrawable=openBtnDrawableId;
		closeDrawable=closeBtnDrawableId;
		 parentLayout= (FrameLayout) (mActivity).getWindow().getDecorView().findViewById(android.R.id.content);
		int childCount=parentLayout.getChildCount();
	
		
		parentLayout.addView(mEazyView,childCount);
		mEazyView.setVisibility(ViewGroup.GONE);
		 childCount=parentLayout.getChildCount();
		 Button btn=(Button) addAButton(mContext);
		parentLayout.addView(btn,childCount);
		
		
		return btn;
		
	}
	
	@SuppressLint("NewApi")
	private View addAButton(Context context) {
		
		//mLaucherBtn.setText("Iam a samplet text");

		mLaucherBtn.setBackgroundResource(openDrawable);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
		            150,
		            150);
		params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.LEFT|Gravity.BOTTOM;
		mLaucherBtn.setLayoutParams(params);
		
		//mLaucherBtn.setRotation(45);
		mLaucherBtn.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				//Toast.makeText(mContext, "i am worng="+mEazyView.getVisibility(), 0).show();
				
				if(mEazyView.getVisibility()==ViewGroup.GONE){
					mEazyView.openView();
					mLaucherBtn.setBackgroundResource(closeDrawable);
				//	mLaucherBtn.setRotation(225);
				}else{
					mEazyView.CloseView();
				//	mLaucherBtn.setRotation(45);
					//setVisibility(ViewGroup.GONE);
					mLaucherBtn.setBackgroundResource(openDrawable);
				}
				
				
				
			}
		});
		return mLaucherBtn;
	}
	
	

}
