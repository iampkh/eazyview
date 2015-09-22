package com.pkh.eazyview.lazyviewreplica;

import com.pkh.eazyview.util.EazyViewUtil;

import android.view.MotionEvent;

public interface EazyViewListener {
	/**
	 * 
	 * @param id : receives the id of the selected option
	 * @param event :Motionevent UP,DOWN
	 * <br/>Desc: onclick api will be triggered during, cliking of any option in the EazySwipeView
	 * 
	 * </br>id return <b> "-1" </b> when id is not set to view
	 */
	public void onclick(int id);
	/**
	 * <br/><b>Note: onScrllingView run in seperate thread, Do update UI using handler</b>
	 * <br/>callback for scrolling (ie) switching a one view to another by scrolling
	 * @param currentQuodrant : current Quodrant 
	 * 
	 * 
	 * 
	 * <br/>there are four quodrant, (default is ONE).
	 * <br/>QUODRANT.ONE -default loading of the layout is ONE
	 * <br/>from ONE if view is swiped/scrolled right then it QUODRANT.TWO
	 * <br/>from TWO if view is swiped/scrolled right then it QUODRANT.THREE
	 * <br/>from THREE if view is swiped/scrolled right then it QUODRANT.FOUR 
	 * OR from ONE if view is swiped/scrolled LEFT then it QUODRANT.FOUR
	 */
	public void onScrollingView(EazyViewUtil.QUODRANT currentQuodrant);
	/**
	 * listener for View Opening and Closing , When eazy view is launched and close 
	 */
	public void onViewOpend();
	public void onViewClosed();
	
}
