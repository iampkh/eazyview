package com.pkh.eazyview.lazyviewreplica;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.pkh.eazyview.util.EazyViewUtil;
import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;
import com.pkh.eazyview.util.EazyViewUtil.ScrollStaus;
import com.pkh.eazyview.util.EazyViewUtil.ViewSize;
import com.pkh.eazyview.util.EazyViewUtil.ViewStatus;
import com.pkh.eazyview.util.OptionView;
import com.pkh.eazyview.util.QuodGroup;
import com.pkh.eazyview.util.ViewHolderDetail;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class EazyView extends View implements OnTouchListener{
	
	Context mContext;
	Activity mActivity;
	EazyViewListener mEazyViewClickListener;
	private Paint canvasPaint=new Paint();
	Paint mHolderViewPaint=new Paint();
	Paint mBitmapIconPaint=new Paint();
	Paint mHolderBorderPaint=new Paint();
	Button mButton;
	/**
	 * initRadius to animate the ARC Background
	 * 
	 */
	private int pInitRadius=0;
	/**
	 * calculation of circle diameter, rectangle height and width;
	 * calculation of mCircle object X and Y axis
	 */
	private int pRectHeight=0;
	private int pRectWidth=0;
	private int pRectX=0;
	private int pRectY=0;
	/**
	 * variable for Quoad (1,2,3,4) ,
	 * variable for xAsix and y axis
	 * Quodrant 1 =(x,y)
	 * Quodrant 2 =(x,-y)
	 * Qrodrant 3 =(-x,-y)
	 * Quodrant 4 =(-x,y)
	 * 
	 */
	//Quodrant 1
	int Quod1X=0;
	int Quod1Y=0;
	//Quodrant 2
	int Quod2X=0;
	int Quod2Y=0;
	//Quodrant 3
	int Quod3X=0;
	int Quod3Y=0;
	//Quodrant 4
	int Quod4X=0;
	int Quod4Y=0;
	
	/**
	 * calc for ARC to be drawn from angle (270 to 360 )degrees (ie) top right quadrant
	 *
	 * calculating for drawArc parameters
	 * startAngle  & sweepAngle
	 */
	int mAngleStartPoint=270;
	int mAngleEndPoint=360;
	
	/**
	 * variable to update or move one part of view to another part
	 */
	float mMoveQuodAngle=0;
	/**
	 * Swiping booleans
	 * if right side is scrolled, then isSwipeRight will be true
	 * if left side is scroled then isSwipeLeft will be true;
	 * 
	 */
	boolean isSwipeRight=false;
	boolean isSwipeLeft=false;
	/**
	 * touchEvent enabling boolean
	 * while UI is in updation, value should be false,to avoid touchevent
	 */
	boolean isTouchEnabled=true;
	/**
	 * each Quodrant angle
	 */
	static final float QUADRANT_ANGLE=90;
	/**
	 * swiping speed
	 */
	static float SWIPE_SPEED=8;
	/**
	 * viewHolderCount dynamic update on adding view
	 */
	private int TOP_VIEWHOLDER_COUNT=5;
	private int BOTTOM_VIEWHOLDER_COUNT=3;
	/**
	 * rotatingQuodrant
	 * Quodrant degree are (0,90,180,270)
	 */
	int mCurrentQuodrant=0;
	
/**
 * variable to manipulate the scroll listener is right or left or nothing
 */
	float startEventYCurrent=0;
	float startEventYPrev=0;
	float startEventXCurrent=0;
	float startEventXPrev=0;
	/**
	 * updating the ENUM for the current Quodrant
	 */
	EazyViewUtil.QUODRANT mEnumCurrentQuodrant;
	//EazyViewUtil.QUODRANT mEnumNxtQuodrant;
	/**
	 *Background color code for ARC
	 */
	private int mArcBgColor=Color.GRAY;
	/** 
	 *transparency for the Arc background 
	 */
	private int mArcBgAlpha=180;
	/**
	 * SCROLL STATUS
	 */
	private ScrollStaus mScrollStatus;
	/**
	 * from the list how many views need to be added
	 */
	private ViewSize mViewSize;
	/**
	 * List of Options to be added in the view
	 */
	
	private ArrayList<OptionView> mOptionList=null;
	/**
	 * curent view status ,(open,idle,close)
	 */
	private ViewStatus pViewStatus=ViewStatus.IDLE;
	/**
	 * list for respective Quodrants
	 */
	private ArrayList<OptionView> mQuodrantOneOptionList=null;
	private ArrayList<OptionView> mQuodrantTwoOptionList=null;
	private ArrayList<OptionView> mQuodrantThreeOptionList=null;
	private ArrayList<OptionView> mQuodrantFourOptionList=null;
	private ArrayList<OptionView> mQuodrantDefaultOptionList=null;
	
	/**
	 * btn drawable id's for open and close of view
	 */
	private int openBtnDrawableId=android.R.drawable.btn_star_big_on;
	private int closeBtnDrawableId=android.R.drawable.btn_star_big_off;
	/**
	 * adding 4 quodrant details with view and view details as list 
	 */
	private HashMap<QUODRANT,QuodGroup> mQuodrantInfo=null;
	
	
	//-------------------------------------User usage api--------------------------------
	
	
	private static Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}
	
	public EazyView(Activity activity,EazyViewListener eazyViewClickListener) {
		// TODO Auto-generated constructor stub
		super(activity.getApplicationContext());
		mContext=activity.getApplicationContext();
		mActivity=activity;
		WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display =windowManager.getDefaultDisplay();
		Point size = getDisplaySize(display);
		
		EazyViewUtil.WINDOW_HEIGHT=size.y;
		EazyViewUtil.WINDOW_WIDTH=size.x;
		
		int width =size.x;
		int height = size.y;
		EazyViewUtil.VIEW_RADIUS=(int) ((int)width*0.75);
		Log.e("pkhtagPixel", "Width="+EazyViewUtil.VIEW_RADIUS+"  dispWidthh="+width);
	
		mEazyViewClickListener=eazyViewClickListener;
		mEnumCurrentQuodrant=QUODRANT.ONE;
		//mEnumNxtQuodrant=QUODRANT.ONE;
		pViewStatus=ViewStatus.CLOSE;
		mScrollStatus=ScrollStaus.SCROLLED;
		
	}
	
	
	/**
	 * 
	 * @param arcBgColor : should be some color code ,
	 * <br/>use :: Color.parseColor("ffffff")  (or)
	 * <br/>usr :: Color.GRAY
	 */
	public void setArcBgColor(int arcBgColor){
		mArcBgColor=arcBgColor;
		
	}
	/**
	 * 
	 * @param arcBgAlpha : should pass value between  (0-255)
	 * <br/> Note1 : arcBgAlpha =0  means full transparent 
	 * <br/> Note2 : arcBgAlpha =255 means no transparent at all
 	 */
	public void setArcBgAlpha(int arcBgAlpha){
		mArcBgAlpha=arcBgAlpha;
	}
	
	/**
	 * <b>Note: optionViewList should not exeed more than 32 </b>
	 * <br/>add properties of each view  and share as a list,
	 * <br/> properties of OptionView are (id,borderColor,Image,QuodrantToBeAdded
	 * 
	 * @param optionViewList  :List of views
	 * @param viewSize : decides how many view need to be displayed from ArrayList
	 * @throws Exception 
	 */
	public void addViews(ArrayList<OptionView> optionViewList,ViewSize viewSize) throws Exception{
		
		ArrayList<OptionView> tmpOptionViewList=new ArrayList<OptionView>();
		ArrayList<OptionView> tmpOptionViewList2=new ArrayList<OptionView>();
		tmpOptionViewList2=optionViewList;
		for(int index=0;index<viewSize.ordinal();index++){
			try{
			tmpOptionViewList.add(optionViewList.get(index));
			}catch(Exception e){
				tmpOptionViewList=tmpOptionViewList2;
				
				throw new Exception("Arraylist.size ="+optionViewList.size()+"_is not eqaulto ViewSize="+viewSize+"_\n " +
						"ViewSize should be less than or equal to  "+ViewSize.values()[optionViewList.size()]+"  \n"+e.toString());
				
			}
		}
		mOptionList=tmpOptionViewList;
		
		mViewSize=viewSize;
		addViewToRespectiveQuodrants();
		Log.e("pkhtagTest", "added views");
		manageQuodrants();
	}
	
	/*public void showView(){
		FrameLayout rootLayout = (FrameLayout)findViewById(android.R.id.content);
		View.inflate(context, resource, root)
	}*/
	
	/**
	 * setSpeed will increase the switching/rotating  speed of Quodrant
	 * @param speedMaxIs10
	 */
	public void setSpeed(float speedMaxIs10) {
		if(speedMaxIs10>10){
			speedMaxIs10=10;
		}else if(speedMaxIs10<1){
			speedMaxIs10=1;
		}
		SWIPE_SPEED=speedMaxIs10;
	}
	
	/**
	 * OPen the EazyView
	 */
	public void openView(){
		setVisibility(ViewGroup.VISIBLE);
		pViewStatus=ViewStatus.OPEN;		
		invalidate();
	}
	/**
	 * OPen the EazyView
	 */
	public void CloseView(){
		pViewStatus=ViewStatus.CLOSE;
		invalidate();
	}
	/**
	 * after changing the properties such as color,alpha,Image,update will refresh the UI
	 */
	public void updateView(){
		invalidate();
	}
	
	/**
	 * 
	 * @param OpenDrawableId : resource id , to set drawable for opening view
	 * @param closeDrawableId :resource id , to set drawable for closing view
	 */
	public void setLauncherBtnDrawable(int OpenDrawableId,int closeDrawableId){
		this.openBtnDrawableId=OpenDrawableId;
		this.closeBtnDrawableId=closeDrawableId;
	}
	
	/**
	 * attach view to activity
	 */
	public void attachEazyViewToActivity() {
		AddViewToLayout mlayout=new AddViewToLayout(mActivity,this);
		mButton=mlayout.addLayout(openBtnDrawableId,closeBtnDrawableId);
	}
	
	//-------------------------------------end of User usage api--------------------------------
	

	private void addViewToRespectiveQuodrants(){
		int Quodrant1Count=8;
		int Quodrant2Count=8;
		int Quodrant3Count=8;
		int Quodrant4Count=8;
		
		mQuodrantOneOptionList=new ArrayList<OptionView>();
		mQuodrantTwoOptionList=new ArrayList<OptionView>();
		mQuodrantThreeOptionList=new ArrayList<OptionView>();
		mQuodrantFourOptionList=new ArrayList<OptionView>();
		mQuodrantDefaultOptionList=new ArrayList<OptionView>();
		
		int OptionSize=mOptionList.size();
		for(int index=0;index<OptionSize;index++){
			QUODRANT quod=mOptionList.get(index).getQuodrant();
			switch (quod) {
			case ONE:
				if (!(mQuodrantOneOptionList.size() >=8)) {
					mOptionList.get(index).setQuodrant(QUODRANT.ONE);
					mQuodrantOneOptionList.add(mOptionList.get(index));
					Quodrant1Count -= 1;
				}else{
					mOptionList.get(index).setQuodrant(QUODRANT.DEFAULT);
					mQuodrantDefaultOptionList.add(mOptionList.get(index));
				}
				break;
			case TWO:
				if (!(mQuodrantTwoOptionList.size() >=8)) {
					mOptionList.get(index).setQuodrant(QUODRANT.TWO);
					mQuodrantTwoOptionList.add(mOptionList.get(index));
					Quodrant2Count -= 1;
				}
				else{
					mOptionList.get(index).setQuodrant(QUODRANT.DEFAULT);
					mQuodrantDefaultOptionList.add(mOptionList.get(index));
				}
				break;
			case THREE:
				if (!(mQuodrantThreeOptionList.size() >=8)) {
					mOptionList.get(index).setQuodrant(QUODRANT.THREE);
					mQuodrantThreeOptionList.add(mOptionList.get(index));
					Quodrant3Count -= 1;
				}
				else{
					mOptionList.get(index).setQuodrant(QUODRANT.DEFAULT);
					mQuodrantDefaultOptionList.add(mOptionList.get(index));
				}
				break;
			case FOUR:
				if (!(mQuodrantFourOptionList.size() >=8)) {
					mOptionList.get(index).setQuodrant(QUODRANT.FOUR);
					mQuodrantFourOptionList.add(mOptionList.get(index));
					Quodrant4Count -= 1;
				}
				else{
					mOptionList.get(index).setQuodrant(QUODRANT.DEFAULT);
					mQuodrantDefaultOptionList.add(mOptionList.get(index));
				}
				break;

			case DEFAULT:
				mQuodrantDefaultOptionList.add(mOptionList.get(index));
				break;
			}
		}
		Log.e("pkhtagQuodrant", "mQuodrantdefOptionList="+mQuodrantDefaultOptionList.size());
		
		
		int defaultListSize=mQuodrantDefaultOptionList.size();
		if(defaultListSize>0){
			int defQuoIndex=0;
			if(Quodrant1Count>0 && Quodrant1Count<=8){
				for(int i=(8-Quodrant1Count);i<8;i++){
					mQuodrantDefaultOptionList.get(defQuoIndex).setQuodrant(QUODRANT.ONE);
					mQuodrantOneOptionList.add(mQuodrantDefaultOptionList.get(defQuoIndex));
					defQuoIndex+=1;
					if(defQuoIndex>defaultListSize-1){
						return;
					}
				}
			}
			if(Quodrant2Count>0 && Quodrant2Count<=8){
				for(int i=(8-Quodrant2Count);i<8;i++){
					mQuodrantDefaultOptionList.get(defQuoIndex).setQuodrant(QUODRANT.TWO);
					mQuodrantTwoOptionList.add(mQuodrantDefaultOptionList.get(defQuoIndex));
					defQuoIndex+=1;
					if(defQuoIndex>defaultListSize-1){
						return;
					}
				}
			}
			if(Quodrant3Count>0 && Quodrant3Count<=8){
				for(int i=(8-Quodrant3Count);i<8;i++){
					mQuodrantDefaultOptionList.get(defQuoIndex).setQuodrant(QUODRANT.THREE);
					mQuodrantThreeOptionList.add(mQuodrantDefaultOptionList.get(defQuoIndex));
					defQuoIndex+=1;
					if(defQuoIndex>defaultListSize-1){
						return;
					}
				}
			}
			if(Quodrant4Count>0 && Quodrant4Count<=8){
				for(int i=(8-Quodrant4Count);i<8;i++){
					mQuodrantDefaultOptionList.get(defQuoIndex).setQuodrant(QUODRANT.FOUR);
					mQuodrantFourOptionList.add(mQuodrantDefaultOptionList.get(defQuoIndex));
					defQuoIndex+=1;
					if(defQuoIndex>defaultListSize-1){
						return;
					}
				}
			}
		}
		
		
	}
	
	private void manageQuodrants(){
		/**
		 * Managing the view to be drawn in all the quodrant
		 */
		int Quodrant1Count=mQuodrantOneOptionList.size();
		int Quodrant2Count=mQuodrantTwoOptionList.size();
		int Quodrant3Count=mQuodrantThreeOptionList.size();
		int Quodrant4Count=mQuodrantFourOptionList.size();
		
		Log.e("pkhsizeCheck", "Quod1="+Quodrant1Count+"_Quodrant2Count="+Quodrant2Count+"_Quodrant3Count="+Quodrant3Count+"_Quodrant4Count="+Quodrant4Count);
		/**
		 * Need to improvise the part of block for keeping same view in all the quordrant
		 */
		if(Quodrant2Count==0 || Quodrant3Count==0 || Quodrant4Count==0){
			if(Quodrant2Count==0 && Quodrant3Count==0 && Quodrant4Count!=0){
				mQuodrantTwoOptionList=mQuodrantFourOptionList;
				mQuodrantThreeOptionList=mQuodrantOneOptionList;
			}else if(Quodrant2Count==0 && Quodrant3Count!=0 && Quodrant4Count==0){
				mQuodrantTwoOptionList=mQuodrantThreeOptionList;
				mQuodrantThreeOptionList=mQuodrantOneOptionList;
				mQuodrantFourOptionList=mQuodrantThreeOptionList;			
			}else if(Quodrant2Count!=0 && Quodrant3Count==0 && Quodrant4Count==0){
				
				mQuodrantThreeOptionList=mQuodrantOneOptionList;
				mQuodrantFourOptionList=mQuodrantTwoOptionList;			
			}else if(Quodrant2Count==0 && Quodrant3Count!=0 && Quodrant4Count!=0){
				mQuodrantTwoOptionList=mQuodrantThreeOptionList;
				mQuodrantThreeOptionList=mQuodrantFourOptionList;
				mQuodrantFourOptionList=mQuodrantThreeOptionList;
			}else if(Quodrant2Count!=0 && Quodrant3Count==0 && Quodrant4Count!=0){
				
				mQuodrantThreeOptionList=mQuodrantFourOptionList;
				mQuodrantFourOptionList=mQuodrantTwoOptionList;
			}else if(Quodrant2Count!=0 && Quodrant3Count!=0 && Quodrant4Count==0){
				mQuodrantFourOptionList=mQuodrantTwoOptionList;
			}
			
			else if(Quodrant2Count==0 && Quodrant3Count==0 && Quodrant4Count==0){
				mQuodrantTwoOptionList=mQuodrantOneOptionList;
				mQuodrantThreeOptionList=mQuodrantOneOptionList;
				mQuodrantFourOptionList=mQuodrantOneOptionList;
			}
		}
		
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		Log.e("pkhView", "LazyView onDraw  ");
		mQuodrantInfo=new HashMap<EazyViewUtil.QUODRANT, QuodGroup>();
		drawBackGround(canvas);
		/**
		 * drawing 4 quodrant views.
		 * 
		 */
		//drawTopHolderViewForQuodrant(canvas,0,mQuodrantOneOptionList);
		try{
		if ((mEnumCurrentQuodrant==QUODRANT.ONE || mScrollStatus==ScrollStaus.SCROLLING ) && mQuodrantOneOptionList != null && mQuodrantOneOptionList.size() > 0) {
			drawBottomHolderViewForQuodrant(canvas, 0, mQuodrantOneOptionList);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		// drawTopHolderViewForQuodrant(canvas,90,mQuodrantTwoOptionList);
		try{
		if ((mEnumCurrentQuodrant==QUODRANT.TWO || mScrollStatus==ScrollStaus.SCROLLING ) && mQuodrantTwoOptionList != null && mQuodrantTwoOptionList.size() > 0) {
			drawBottomHolderViewForQuodrant(canvas, 90, mQuodrantTwoOptionList);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		// drawTopHolderViewForQuodrant(canvas,180,mQuodrantThreeOptionList);
		try{
		if ((mEnumCurrentQuodrant==QUODRANT.THREE || mScrollStatus==ScrollStaus.SCROLLING ) && mQuodrantThreeOptionList != null && mQuodrantThreeOptionList.size() > 0) {
			drawBottomHolderViewForQuodrant(canvas, 180,mQuodrantThreeOptionList);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		// drawTopHolderViewForQuodrant(canvas,270,mQuodrantFourOptionList);
		try{
		if ((mEnumCurrentQuodrant==QUODRANT.FOUR || mScrollStatus==ScrollStaus.SCROLLING ) && mQuodrantFourOptionList != null	&& mQuodrantFourOptionList.size() > 0) {
			drawBottomHolderViewForQuodrant(canvas, 270,mQuodrantFourOptionList);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		pViewStatus=openCloseView(pViewStatus);
		
		if(isSwipeLeft || isSwipeRight){
			movetoOtherPartOfView();
		}
	        
	}
	
	private ViewStatus openCloseView(ViewStatus mViewStatus) {
		/**
		 * increasint the radius to reach 70% of width
		 */
		switch (mViewStatus) {
		case OPEN:
			if( pInitRadius<EazyViewUtil.VIEW_RADIUS){
				this.setVisibility(ViewGroup.VISIBLE);
				pInitRadius+=85;
				isTouchEnabled=false;
				
				
			}else{
				pInitRadius=EazyViewUtil.VIEW_RADIUS;
				mViewStatus=ViewStatus.IDLE;
				isTouchEnabled=true;
				mEazyViewClickListener.onViewOpend();
				
			}
			invalidate();
			break;
		case IDLE:
			mViewStatus=ViewStatus.IDLE;
			isTouchEnabled=true;
			break;
		case CLOSE:
			if( pInitRadius>0){
				pInitRadius-=85;
				isTouchEnabled=false;				
			}else{
				mEazyViewClickListener.onViewClosed();
				pInitRadius=0;
				mViewStatus=ViewStatus.CLOSE;
				isTouchEnabled=true;
				this.setVisibility(ViewGroup.GONE);
				/**
				 * Rotating the button to 45 degree, so that arrow will rotate back;
				 */
				mButton.setBackgroundResource(openBtnDrawableId);
				
			}
			invalidate();
			break;
		default:
			
			break;
		}
		
		return mViewStatus;
	}
	
	private void drawBackGround(Canvas canvas) {
		
		/**
		 * adding properties of rectangle
		 */
		int rectLeft=-pInitRadius;
		int rectTop=getHeight()-pInitRadius;
		int rectRight=pInitRadius;
		int rectBottom=getHeight()+pInitRadius;
		
		/**
		 * updating Quodrant value
		 */
		Quod1X=rectLeft+pInitRadius;
		Quod1Y=rectTop;	
		
		/**
		 * updating X and Y axis of background Circle (ARC) or Rectangle 
		 * updating Height and width of Circle (ARC) or Rectangle
		 */
		pRectX=rectLeft;
		pRectY=rectTop;
		pRectHeight=pInitRadius*2;
		pRectWidth=pInitRadius*2;
		
		
		int sweepAngle=mAngleEndPoint-mAngleStartPoint;
		
		
		/**
		 * RectF to create a box, where arc will be created from 270 to 360 
		 */
		RectF rect=new RectF();
		rect.set(rectLeft,rectTop, rectRight, rectBottom);
		
		/**
		 * adding color to ARC (dark gray)
		 * adding transparency to ARC
		 */
		canvasPaint.setColor(mArcBgColor);		
		canvasPaint.setAlpha(mArcBgAlpha);
		canvas.drawArc(rect, mAngleStartPoint, sweepAngle, true,canvasPaint);
		
		
		

	}
	/**
	 * method to draw ,Top Holer view for each Quodrant (each part)
	 * @param canvas : drawing object
	 * @param quodrantAngle : for drawing circle(holder) in particular quodrant start point
	 *          (ie) Quodrant 1 :0   , Quodrant 2 : 90   ,Quodrant 3:180    , Quodrant 4 :270
	 */
	private QuodGroup drawTopHolderViewForQuodrant(Canvas canvas,float quodrantAngle,ArrayList<OptionView> optionView,QuodGroup quodInfo) {
		//int initialIndex=BOTTOM_VIEWHOLDER_COUNT;
		QUODRANT quodName=optionView.get(0).getQuodrant();
		int topIndex=BOTTOM_VIEWHOLDER_COUNT;
		int viewCount=optionView.size()-topIndex;
		
		/**
		 * view holder (small circle) space calculation
		 * dividing the quodrant into 9 parts and plotting the view
		 */
		float angleDiff=(float)((EazyViewUtil.CIRCULAR_ANGLE-mAngleStartPoint)/(viewCount*2));		
		
		/**
		 * all view drawn in a circular path
		 * of 90 % of actual radius 
		 */
		float holderPathRadius=(float)pInitRadius*0.85f;
		/**
		 * view holder radius
		 */
		float holderRadius=pInitRadius/9;	
		
		//mHolderViewPaint.setColor(Color.MAGENTA);		
		ArrayList<ViewHolderDetail> viewDetailsList=new ArrayList<ViewHolderDetail>();
		int i=1;
		do {
			ViewHolderDetail viewDetail=new ViewHolderDetail();
			
			OptionView locOptionView=optionView.get(topIndex);
			
			viewDetail.setViewId(locOptionView.getViewId());
			
			float holder1X=(float) ((Quod1X)+(holderPathRadius*(Math.sin(Math.toRadians(quodrantAngle+(angleDiff*i)+mMoveQuodAngle)))));
			float holder1Y=(float) ((getHeight())-(holderPathRadius*(Math.cos(Math.toRadians(quodrantAngle+(angleDiff*i)+mMoveQuodAngle)))));
		//	mHolderViewPaint.setColor(Color.RED);	
			/**
			 * canvs line from origin to holder
			 */
			//canvas.drawLine(0, getHeight(), holder1X, holder1Y, mHolderViewPaint);
				
			/**
			 * cicle for holder border
			 */
			mHolderBorderPaint.setColor(locOptionView.getOptionHolderBorderColor());
			mHolderBorderPaint.setStyle(Style.STROKE);
			mHolderBorderPaint.setStrokeWidth(locOptionView.getBorderWidth());
			canvas.drawCircle(holder1X, holder1Y,holderRadius , mHolderBorderPaint);			
			/**
			 * circle for holder addinv details to library
			 */
			float holderRadiusInner=holderRadius*(0.90f);

			viewDetail.setQuodrant(quodName);
			viewDetail.setViewOrginX(holder1X);
			viewDetail.setViewOriginY(holder1Y);
			float viewYbottomRight=viewDetail.getViewOriginY()+holderRadiusInner;
			float viewXbottomRight=viewDetail.getViewOrginX()+holderRadiusInner;
			viewDetail.setPosXbottomRight(viewXbottomRight);
			viewDetail.setPosYbottomRight(viewYbottomRight);
			float viewYtopLeft=viewDetail.getViewOriginY()-holderRadiusInner;
			float viewXtopLeft=viewDetail.getViewOrginX()-holderRadiusInner;
			viewDetail.setPosXtopLeft(viewXtopLeft);
			viewDetail.setPosYtopLet(viewYtopLeft);
			
			/**
			 * circle for holder drawing
			 */
			mHolderViewPaint.setAlpha(locOptionView.getAlpha());
			mHolderViewPaint.setColor(locOptionView.getOptionHolderBackgroundColor());
			canvas.drawCircle(holder1X, holder1Y,holderRadiusInner , mHolderViewPaint);
			
			/**
			 * calculation to image (icon) holder x and y
			 */
			float holderIconRadius=(float) (holderRadiusInner*0.75);
			float diffOfRadius=holderRadiusInner-holderIconRadius;
			float iconPosX=viewDetail.getPosXtopLeft()+diffOfRadius;
			float iconPosY=viewDetail.getPosYtopLeft()+diffOfRadius;
			
			//if(viewDetail.getQuodrant()==mEnumNxtQuodrant){
			if(locOptionView.getImageBitmap()!=null && viewDetail.getPosXbottomRight()>0 && viewDetail.getPosYbottomRight()<getHeight()){
				Bitmap origIcon=locOptionView.getImageBitmap();
				if(origIcon!=null && ((float)holderRadiusInner>0)){
				Bitmap viewIcon=getResizedBitmap(locOptionView.getImageBitmap(),(float)holderIconRadius*2, (float) (holderRadiusInner*0.75)*2);
				canvas.drawBitmap(viewIcon, iconPosX, iconPosY, mBitmapIconPaint);
				}
			}
			viewDetailsList.add(viewDetail);
			//skipping even seperators
			i=i+2;
			topIndex+=1;
		} while (i<(viewCount*2));  //dividing the one quodrant into 10 and drawing the circle(holder) in even line(1,3,5,7,9)
		
		quodInfo.setHolderDetails(viewDetailsList);
		
		return quodInfo;
	}
	
	/**
	 * method to draw , Bottom Holer view for each Quodrant (each part)
	 * @param canvas : drawing object
	 * @param quodrantAngle : for drawing circle(holder) in particular quodrant start point
	 *          (ie) Quodrant 1 :0   , Quodrant 2 : 90   ,Quodrant 3:180    , Quodrant 4 :270
	 */
	private void drawBottomHolderViewForQuodrant(Canvas canvas,float quodrantAngle,ArrayList<OptionView> optionViewList) {
		
		QUODRANT quodName=optionViewList.get(0).getQuodrant();
		/**
		 * quodInfo uses for identifying the click listener for particular circle
		 */
		QuodGroup quodInfo=new QuodGroup();
		quodInfo.setQuodrantName(quodName);
		
		int viewCount=optionViewList.size();
		int bottomIndex=0;
		/**
		 * all view drawn in a circular path
		 * of particular % of actual radius 
		 */
		float holderPathRadius=(float)pInitRadius*0.50f;
			/**
			 * if total item in the holder is 4, then drawing the view in the centre of the ARC
			 */
		if(viewCount==4){
			holderPathRadius=(float)pInitRadius*0.65f;
		}else if(viewCount<4){
			holderPathRadius=(float)pInitRadius*0.50f;
		}
		
		if(viewCount>4){
			viewCount=BOTTOM_VIEWHOLDER_COUNT;
			holderPathRadius=(float)pInitRadius*0.50f;
			QuodGroup tmpArray=drawTopHolderViewForQuodrant(canvas,quodrantAngle,optionViewList,quodInfo);
			if(tmpArray!=null){
				quodInfo=tmpArray;
			}
		}
		
		/**
		 * view holder (small circle) space calculation
		 * dividing the quodrant into 9 parts and plotting the view
		 */
		float angleDiff=(float)((EazyViewUtil.CIRCULAR_ANGLE-mAngleStartPoint)/(viewCount*2));		
		
		
		/**
		 * view holder radius
		 */
		float holderRadius=pInitRadius/9;	
		
		mHolderViewPaint.setColor(Color.GRAY);
		ArrayList<ViewHolderDetail>tmpholderlist=quodInfo.getHolderDetails();
		ArrayList<ViewHolderDetail> viewDetailsList=null;
		if(tmpholderlist==null){
			viewDetailsList=new ArrayList<ViewHolderDetail>();
		}else{
			viewDetailsList=tmpholderlist;
		}
		
		int i=1;
		do {
			ViewHolderDetail viewDetail=new ViewHolderDetail();
			OptionView locOptionView=optionViewList.get(bottomIndex);
			
			viewDetail.setViewId(locOptionView.getViewId());
			
			float holder1X=(float) ((Quod1X)+(holderPathRadius*(Math.sin(Math.toRadians(quodrantAngle+(angleDiff*i)+mMoveQuodAngle)))));
			float holder1Y=(float) ((getHeight())-(holderPathRadius*(Math.cos(Math.toRadians(quodrantAngle+(angleDiff*i)+mMoveQuodAngle)))));
			//mHolderViewPaint.setColor(Color.RED);	
			/**
			 * canvs line from origin to holder
			 */
			//canvas.drawLine(0, getHeight(), holder1X, holder1Y, mHolderViewPaint);
				
			/**
			 * cicle for holder border
			 */
			mHolderBorderPaint.setColor(locOptionView.getOptionHolderBorderColor());
			mHolderBorderPaint.setStyle(Style.STROKE);
			mHolderBorderPaint.setStrokeWidth(locOptionView.getBorderWidth());
			canvas.drawCircle(holder1X, holder1Y,holderRadius , mHolderBorderPaint);
			
						
			/**
			 * circle for holder, addinv info to viewHoder
			 */
			float holderRadiusInner=holderRadius*(0.90f);
			viewDetail.setQuodrant(quodName);
			viewDetail.setViewOrginX(holder1X);
			viewDetail.setViewOriginY(holder1Y);
			float viewYbottomRight=viewDetail.getViewOriginY()+holderRadiusInner;
			float viewXbottomRight=viewDetail.getViewOrginX()+holderRadiusInner;
			viewDetail.setPosXbottomRight(viewXbottomRight);
			viewDetail.setPosYbottomRight(viewYbottomRight);
			float viewYtopLeft=viewDetail.getViewOriginY()-holderRadiusInner;
			float viewXtopLeft=viewDetail.getViewOrginX()-holderRadiusInner;
			viewDetail.setPosXtopLeft(viewXtopLeft);
			viewDetail.setPosYtopLet(viewYtopLeft);
			
			/**
			 * circle for holder, 
			 */
			mHolderViewPaint.setAlpha(locOptionView.getAlpha());
			mHolderViewPaint.setColor(locOptionView.getOptionHolderBackgroundColor());
			canvas.drawCircle(holder1X, holder1Y,holderRadiusInner , mHolderViewPaint);
			/**
			 * calculation to image (icon) holder x and y
			 */
			float holderIconRadius=(float) (holderRadiusInner*0.75);
			float diffOfRadius=holderRadiusInner-holderIconRadius;
			float iconPosX=viewDetail.getPosXtopLeft()+diffOfRadius;
			float iconPosY=viewDetail.getPosYtopLeft()+diffOfRadius;
			
			//if(viewDetail.getQuodrant()==mEnumNxtQuodrant){
			if(locOptionView.getImageBitmap()!=null && viewDetail.getPosXbottomRight()>0 && viewDetail.getPosYbottomRight()<getHeight()){
				Bitmap origIcon=locOptionView.getImageBitmap();
				if(origIcon!=null && ((float)holderRadiusInner>0)){
				Bitmap viewIcon=getResizedBitmap(locOptionView.getImageBitmap(),(float)holderIconRadius*2, (float) (holderRadiusInner*0.75)*2);
				canvas.drawBitmap(viewIcon, iconPosX, iconPosY, mBitmapIconPaint);
				}
			}
			//Log.d(EazyViewUtil.TAG, "Eazyview Quodrant="+quodrantAngle+"_i="+i);;
			if(viewDetailsList!=null){
				viewDetailsList.add(viewDetail);
				
			}
			
			//skipping even seperators
			i=i+2;
			bottomIndex+=1;
		} while (i<(viewCount*2));  //dividing the one quodrant into 10 and drawing the circle(holder) in even line(1,3,5,7,9)
	
		mQuodrantInfo.put(quodName, quodInfo);
	}
	
	/**
	 * idetifying the hypotaneous of particular triantle
	 * @param width
	 * @param height
	 * @return
	 */
	private float getHypotaneous(float width,float height ){
		float widthSqre=width*width;
		float heightSqre=height*height;
		float sqresum=widthSqre+heightSqre;
		
		float hypo=(float)Math.sqrt(sqresum);
		
		return hypo;
	}
	/**
	 * 
	 */
	private float getAngleInRadians(float opposite,float adjacent){
		//float angleInPxl=(opposite/adjacent);
		//float angleinRadian=(float) Math.toRadians(angleInPxl);
		float angleInPxl=(float) Math.atan2(opposite, adjacent);
		return (float)Math.toRadians(angleInPxl);
	}
	
	/**
	 * 
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @return new resized Bitmap
	 */
	public Bitmap getResizedBitmap(Bitmap bm, float newHeight, float newWidth)
	{
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) /(float) width;
	    float scaleHeight = ((float) newHeight) /(float) height;
	    Log.e("pkhtagResize", "Bimp width="+newWidth+"_height="+newHeight+"__");
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    
	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	private void updateScrollListener(int currentQuodrantValue) {
		//-------------------updating the listener-------------------------
		/**Updaitng quodrant as scrolling is done
		 * by reading the current quodrant enum and updating by adding one, 
		 */
		
		if(currentQuodrantValue>4){
			currentQuodrantValue=1;
		}
		if(currentQuodrantValue<1){
			currentQuodrantValue=4;
		}
		mEnumCurrentQuodrant=QUODRANT.values()[currentQuodrantValue];
		/**
		 * updating the onscroll listener using thread, So that if user handles heavy operation 
		 * will be done/handled without blocking UI
		 */
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mEazyViewClickListener.onScrollingView(mEnumCurrentQuodrant);
			}
		}).start();
		
		//------------------end of updating the listener-------------------------------------------
		

	}
	
	/**
	 * Updating the scrolling listener, moving a view to another quodrant
	 */
	private void movetoOtherPartOfView() {
		Log.e("pkhtag", "MoveToview right="+isSwipeRight+"_isLeft="+isSwipeLeft);
		if(isSwipeRight){	
			isSwipeLeft=false;
			Log.e("pkhtags", "mMoeqod="+mMoveQuodAngle+"_mCurrentQuodrant+90="+(mCurrentQuodrant+QUADRANT_ANGLE));
			//if right Swipe  movngQuodAngle is less than nxtQuad angle then increase moveQuoadAngle by (moveQuodangle+Speed)
			if(mMoveQuodAngle<mCurrentQuodrant+QUADRANT_ANGLE){  
				isTouchEnabled=false;
				mScrollStatus=ScrollStaus.SCROLLING;
				/**
				 * updating the info about next Quodrant
				 */
				/*int currentQuodrantValue=mEnumCurrentQuodrant.ordinal()-1;
				if(currentQuodrantValue>4){
					currentQuodrantValue=1;
				}
				if(currentQuodrantValue<1){
					currentQuodrantValue=4;
				}
				mEnumNxtQuodrant=QUODRANT.values()[currentQuodrantValue];*/
				
				//---------end of updating info about nxt quodrant------------
				mMoveQuodAngle+=SWIPE_SPEED;
				if(mMoveQuodAngle>mCurrentQuodrant+QUADRANT_ANGLE){
					mMoveQuodAngle=mCurrentQuodrant+QUADRANT_ANGLE;
				}
				invalidate();
				
			}else{
				//updating the scroll listener
				updateScrollListener(mEnumCurrentQuodrant.ordinal()-1);
				
				mCurrentQuodrant+=QUADRANT_ANGLE;
				mMoveQuodAngle=mCurrentQuodrant;
				if(mCurrentQuodrant>=EazyViewUtil.CIRCULAR_ANGLE){
					mCurrentQuodrant=0;
				}
				if(mMoveQuodAngle>=EazyViewUtil.CIRCULAR_ANGLE){
					mMoveQuodAngle=0;
				}
				isTouchEnabled=true;
				isSwipeRight=false;
				mScrollStatus=ScrollStaus.SCROLLED;
				
			}
			
		}else if(isSwipeLeft){
			isSwipeRight=false;
			Log.e("pkhtags", "mMoeqod="+mMoveQuodAngle+"_mCurrentQuodrant-90="+(mCurrentQuodrant-QUADRANT_ANGLE));
			//if left Swipe  movngQuodAngle is greater than nxtQuad angle then decrease moveQuoadAngle by (moveQuodangle-Speed)
			if(mMoveQuodAngle>mCurrentQuodrant-QUADRANT_ANGLE){
				isTouchEnabled=false;
				mScrollStatus=ScrollStaus.SCROLLING;
				/**
				 * updating the info about next Quodrant
				 */
				/*int currentQuodrantValue=mEnumCurrentQuodrant.ordinal()+1;
				if(currentQuodrantValue>4){
					currentQuodrantValue=1;
				}
				if(currentQuodrantValue<1){
					currentQuodrantValue=4;
				}
				mEnumNxtQuodrant=QUODRANT.values()[currentQuodrantValue];*/
				
				//---------end of updating info about nxt quodrant------------
				
				mMoveQuodAngle-=SWIPE_SPEED;
				if(mMoveQuodAngle<mCurrentQuodrant-QUADRANT_ANGLE){
					mMoveQuodAngle=mCurrentQuodrant-QUADRANT_ANGLE;
				}
				invalidate();
			}else{
				//updating the scroll listener
				updateScrollListener(mEnumCurrentQuodrant.ordinal()+1);
				mCurrentQuodrant-=QUADRANT_ANGLE;
				mMoveQuodAngle=mCurrentQuodrant;
				if(mCurrentQuodrant<=-EazyViewUtil.CIRCULAR_ANGLE){
					mCurrentQuodrant=0;
				}
				if(mMoveQuodAngle<=-EazyViewUtil.CIRCULAR_ANGLE){
					mMoveQuodAngle=0;
				}
				isTouchEnabled=true;
				isSwipeLeft=false;
				mScrollStatus=ScrollStaus.SCROLLED;
			}
		}
		Log.e("pkhtags", "mCurrentQoud="+mCurrentQuodrant);
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		
		/*isSwipeRight=false;
		isSwipeLeft=true;*/
		//movetoOtherPartOfView();
		
		int action=event.getAction();
		
		switch (action) {
		case MotionEvent.ACTION_MOVE:
			//calculating right or left swipe
			startEventYCurrent=event.getY();
			startEventXCurrent=event.getX();
		//	Log.e("pkhtags", "TouchEvent  startEventYCurrent="+startEventYCurrent+"_startEventYPrev="+startEventYPrev+"_Diff="+(startEventYCurrent-startEventYPrev));
			if((startEventYPrev!=0 && startEventXPrev!=0) &&((startEventXPrev<startEventXCurrent)&& (startEventYPrev<startEventYCurrent)) && (startEventYCurrent-startEventYPrev)>70){
				//swiperight
				isSwipeRight=true;
				isSwipeLeft=false;
				
				
			}else if((startEventYPrev!=0 && startEventXPrev!=0) &&((startEventXPrev>startEventXCurrent)&& (startEventYPrev>startEventYCurrent))  && (startEventYPrev-startEventYCurrent)>70){
				//swipeleft
				isSwipeLeft=true;
				isSwipeRight=false;
								
			}else if((startEventYPrev!=0 && startEventXPrev!=0) &&((startEventXPrev<startEventXCurrent)&& (startEventYPrev>startEventYCurrent))&& (startEventYPrev-startEventYCurrent)>70){
				//View opening update
				pViewStatus=ViewStatus.OPEN;
			}else if((startEventYPrev!=0 && startEventXPrev!=0) &&((startEventXPrev>startEventXCurrent)&& (startEventYPrev<startEventYCurrent))&& (startEventYCurrent-startEventYPrev)>70){
				//view close update
				pViewStatus=ViewStatus.CLOSE;
			}
			startEventYPrev=startEventYCurrent;
			startEventXPrev=startEventXCurrent;
			invalidate();
		
			//end of calculating right or left swipe
			break;
		case MotionEvent.ACTION_UP:
			/**
			 * updating the view 
			 */
			if( pInitRadius>EazyViewUtil.VIEW_RADIUS){
				pInitRadius=EazyViewUtil.VIEW_RADIUS;
			}else if(pInitRadius<0){
				pInitRadius=0;
			}
			//------------------view click listener manipulation----------------
			manipulateViewClick(event.getX(), event.getY());
			
			
			//---------------------Swipe Right/Left manipulation-------------------
			movetoOtherPartOfView();
			startEventYCurrent=0;
			startEventYPrev=0;
			Log.e("pkhtags", "TouchEvent  isSwipeRight="+isSwipeRight+"_isSwipeLeft="+isSwipeLeft);;
			
			invalidate();
			
			break;
		case MotionEvent.ACTION_DOWN:
					
			break;

		default:
			break;
		}
				
		return isTouchEnabled;
		
		//return super.onTouchEvent(event);
	}
	
	/**
	 * method to identify whether the option is clicked or outside view is clicked
	 * @param posX
	 * @param posY
	 */
	private void manipulateViewClick(float posX,float posY) {
		if(!isSwipeLeft && !isSwipeRight && isTouchEnabled){
			QuodGroup quodGroup=mQuodrantInfo.get(mEnumCurrentQuodrant);
			ArrayList<ViewHolderDetail> quodView=null;
			int quodViewSize=0;
			if(quodGroup!=null){
			 quodView=quodGroup.getHolderDetails();
			 quodViewSize=quodView.size();
			}
			
			if(quodView!=null && quodViewSize>0){
				for(int index=0;index<quodViewSize;index++){
					
					float holderX1Pos=quodView.get(index).getPosXtopLeft();
					float holderX2Pos=quodView.get(index).getPosXbottomRight();
					float holderY1Pos=quodView.get(index).getPosYtopLeft();
					float holderY2Pos=quodView.get(index).getPosYbottomRight();
					
					if(posX>holderX1Pos && posX<holderX2Pos && posY>holderY1Pos && posY<holderY2Pos){
						int viewId=quodView.get(index).getViewId();
						mEazyViewClickListener.onclick(viewId);
					}
					
				}
				
			}
			
		}

	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
