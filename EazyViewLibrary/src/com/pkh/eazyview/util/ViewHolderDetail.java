package com.pkh.eazyview.util;

import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;

public class ViewHolderDetail {
	/**
	 * presents of view in which Quodrant 
	 */
	private QUODRANT mQuodrant;
	/**
	 * pixel x of the cirlce centre
	 */
	private float mViewOrginX;
	/**
	 * pixel y of the cirlce centre
	 */
	private float mViewOriginY;
	/**
	 * pixel x of the Topleft corner of the circle (rect)
	 */
	private float mPosXtopLeft;
	/**
	 * pixel y of the Topleft corner of the circle (rect)
	 */
	private float mPosYtopLeft;
	/**
	 * pixel x of the Bottomleft corner of the circle (rect)
	 */
	private float mPosXbottomRight;
	/**
	 * pixel y of the Bottomleft corner of the circle (rect)
	 */
	private float mPosYbottomRight;
	/**
	 * view id used by user
	 */
	private int viewId;
	
	public int getViewId() {
		return viewId;
	}
	public void setViewId(int viewId) {
		this.viewId = viewId;
	}
	public QUODRANT getQuodrant() {
		return mQuodrant;
	}
	public void setQuodrant(QUODRANT mQuodrant) {
		this.mQuodrant = mQuodrant;
	}
	public float getViewOrginX() {
		return mViewOrginX;
	}
	public void setViewOrginX(float mViewOrginX) {
		this.mViewOrginX = mViewOrginX;
	}
	public float getViewOriginY() {
		return mViewOriginY;
	}
	public void setViewOriginY(float mViewOriginY) {
		this.mViewOriginY = mViewOriginY;
	}
	public float getPosXtopLeft() {
		return mPosXtopLeft;
	}
	public void setPosXtopLeft(float mPosX) {
		this.mPosXtopLeft = mPosX;
	}
	public float getPosYtopLeft() {
		return mPosYtopLeft;
	}
	public void setPosYtopLet(float mPosY) {
		this.mPosYtopLeft = mPosY;
	}
	public float getPosXbottomRight() {
		return mPosXbottomRight;
	}
	public void setPosXbottomRight(float mPosX) {
		this.mPosXbottomRight = mPosX;
	}
	public float getPosYbottomRight() {
		return mPosYbottomRight;
	}
	public void setPosYbottomRight(float mPosY) {
		this.mPosYbottomRight = mPosY;
	}
	

}
