package com.pkh.eazyview.lazyviewreplica;

import com.pkh.eazyview.util.EazyViewUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class AddViewToLayout {
	private Context mContext;
	private Activity mActivity;
	private EazyView mEazyView;
	Button mLaucherBtn=null;
	int launcherBtnSize=75;
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
	public Button addLayout(int openBtnDrawableId,int closeBtnDrawableId,int launcherButtonSize) {

		openDrawable=openBtnDrawableId;
		closeDrawable=closeBtnDrawableId;
		launcherBtnSize=launcherButtonSize;
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
	private View addAButton(final Context context) {
		
		//mLaucherBtn.setText("Iam a samplet text");

		mLaucherBtn.setBackgroundResource(openDrawable);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				launcherBtnSize,
				launcherBtnSize);
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
					int orientation=context.getResources().getConfiguration().orientation;
					
						EazyViewUtil.VIEW_RADIUS=calculateRadius(orientation);
					
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
	
	private int calculateRadius(int Orientation) {
		WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display =windowManager.getDefaultDisplay();
		Point size = EazyView.getDisplaySize(display);
		
		EazyViewUtil.WINDOW_HEIGHT=size.y;
		EazyViewUtil.WINDOW_WIDTH=size.x;
		
		int width =size.x;
		int height = size.y;
		
		if(Orientation==Configuration.ORIENTATION_LANDSCAPE){
			return (int) ((int)height*0.75);
		}else if(Orientation==Configuration.ORIENTATION_PORTRAIT){
			return (int) ((int)width*0.75);
		}
		
		return (int) ((int)width*0.75);
	}
	

}
