package com.pkh.eazyview.util;

import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;

public class OptionView {
	/**
	 * holder properties
	 */
	int mOptionHolderBorderColor=Color.GRAY;
	int mOptionHolderBackgroundColor=Color.MAGENTA;
	int mOptoinHolderId=-1;
	Bitmap mViewImageBitmap=null;
	/**
	 * bmp properties
	 */
	QUODRANT mQuodrant=QUODRANT.DEFAULT;
	Context mContext;
	int alpha=180;
	int borderWidth=10;
	/**
	 * text properties
	 */
	private int defTxtSize=70;
	int textSize=100;
	String text=null;
	int textColor=Color.WHITE;
	
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
	
	/**
	 * <b>Note: Text should be a two character, so the letter will be added good.</b>
	 * <br/>default character is NULL, 
	 * <br/>returns empty charater if charcter is null
	 * @param char
	 */
	public void setText(String stringWith2Char){
		
		this.text=stringWith2Char.length() <=3 ? stringWith2Char : stringWith2Char.substring(1, 3);
	}
	public String getText(){
		if(text==null){
			
			return "";
		}
		return this.text;
	}
	

	/**
	 * <b>Note: TextSize should be a greater than 50 and less than 120 .</b>
	 * <br/>default size  is 100, 
	 * <br/>
	 * @param int
	 */
	public void setTextSize(int size){
		if(size<120){
			if(text.length()<2){
				this.textSize=size;
			}else{
				this.textSize=size<defTxtSize ? size:defTxtSize;
			}
		}
	}
	public int getTextSize(){
		if(text.length()<2){
			return this.textSize;
		}else{
			this.textSize=this.textSize<defTxtSize ? this.textSize:defTxtSize;
			return this.textSize;
		}
		
	}
	/**
	 * <b>Note: TextSize should be a greater than 50 and less than 150 .</b>
	 * <br/>default size  is 100, 
	 * <br/>
	 * @param int
	 */
	public void setTextColor(int color){
		this.textColor=color;
	}
	public int getTextColor(){
		
		return this.textColor;
	}

}
