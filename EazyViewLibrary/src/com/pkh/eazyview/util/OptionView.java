package com.pkh.eazyview.util;

import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class OptionView {
	int mOptionHolderBorderColor=Color.GRAY;
	int mOptionHolderBackgroundColor=Color.MAGENTA;
	int mOptoinHolderId=-1;
	Bitmap mViewImageBitmap=null;
	QUODRANT mQuodrant=QUODRANT.DEFAULT;
	Context mContext;
	int alpha=180;
	int borderWidth=10;
	
	private OptionView(){
		
	}
	public OptionView(Context mContext) {
		this.mContext=mContext;
	}
	
	/**
	 * Default width is (10);
	 * @param borderWid : border width of the option
	 */
	public void setBorderWidth(int borderWid){
		this.borderWidth=borderWid;
	}
	public int getBorderWidth(){
		return this.borderWidth;
	}
	/**
	 * 
	 * @param arcBgAlpha : should pass value between  (0-255)
	 * <br/> Note1 : arcBgAlpha =0  means full transparent 
	 * <br/> Note2 : arcBgAlpha =255 means no transparent at all
 	 */
	public void setAlpha(int alpha){
		this.alpha=alpha;
	}
	public int getAlpha(){
		return this.alpha;
	}
	public int getOptionHolderBorderColor() {
		return mOptionHolderBorderColor;
	}
	/**
	 * 
	 * @param color  : Color code to update the OptionView border
	 */
	public void setOptionHolderBorderColor(int color) {
		this.mOptionHolderBorderColor = color;
	}
	public int getOptionHolderBackgroundColor() {
		return mOptionHolderBackgroundColor;
	}
	/**
	 * 
	 * @param color : Color code to update the bacground of view
	 */
	public void setOptionHolderBackgroundColor(int color) {
		this.mOptionHolderBackgroundColor = color;
	}
	public int getViewId() {
		return mOptoinHolderId;
	}
	/**
	 * 
	 * @param id : to unique identification
	 */
	public void setViewId(int id) {
		this.mOptoinHolderId = id;
	}
	
	public Bitmap getImageBitmap() {
		return mViewImageBitmap;
	}
	
	/**
	 * 
	 * @param drawableId : Id which refers to R.drwabale.imageresource
	 */
	public void setImage(int drawableId) {
		
		Bitmap tempBitmap = BitmapFactory.decodeResource(mContext.getResources(),drawableId);
		this.mViewImageBitmap=Bitmap.createScaledBitmap(tempBitmap, 100, 100, true);
	}
	/**
	 * 
	 * @param bitmpa : refers to Bitmap image
	 */
	public void setImage(Bitmap bitmap) {
		
		this.mViewImageBitmap = bitmap;
	}
	/**
	 * <b>Note: Each Quodrant can hold 8 view, total view count should not exist more than 32.</b>
	 * <br/>Quodrant ONE is default, 
	 * <br/>Specify Quodrant to make the view to stick with particular Quodrant 
	 * @param mQuodrant
	 */
	public void setQuodrant(QUODRANT mQuodrant) {
		this.mQuodrant = mQuodrant;
	}
	public QUODRANT getQuodrant() {
		return mQuodrant;
	}
	

}
