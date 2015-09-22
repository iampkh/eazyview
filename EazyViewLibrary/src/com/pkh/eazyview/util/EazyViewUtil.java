package com.pkh.eazyview.util;

public class EazyViewUtil {
	public static final String TAG="EazyViewTAG";
	/**
	 * Display (Window) Height and Width
	 */
	public static float WINDOW_WIDTH=0;
	public static float WINDOW_HEIGHT=0; 
	/**
	 * RADIUS to draw semi circle (ARC)
	 *  
	 */
	public static  int VIEW_RADIUS=900;
	public static int BASIC_SWIPE_DISTANCE=200;
	/**
	 * Full Circle Angle(0-360)
	 */
	public static final float CIRCULAR_ANGLE=360;
	/**
	 * Enum for quodrant (1,2,34)
	 */
	public enum QUODRANT{
		DEFAULT,ONE,TWO,THREE,FOUR
	}
	/**
	 * Enum to avoid adding views more than 32
	 *
	 */
	public enum ViewSize{
		zero,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,
		ELEVEN,TWELVE,THIRTEEN,FOURTEEN,FIFTEEN,SIXTEEN,SEVENTEEN,EIGHTEEN,NINTEEN,TWENTY,
		TWENTY_ONE,TWENTY_TWO,TWENTY_THREE,TWENTY_FOUR,TWENTY_FIVE,TWENTY_SIX,TWENTY_SEVEN,TWENTY_EIGHT,TWENTY_NINE,THIRTY,
		THIRTY_ONE,THIRTY_TWO
	}
	/**
	 * Enum for the staus of Close and Open a view
	 */
	public enum ViewStatus{
		OPEN,IDLE,CLOSE
	}
	/**
	 * Enum for scroll staus
	 */
	public enum ScrollStaus{
		SCROLLING,SCROLLED
	}
}
