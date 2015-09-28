package com.pkh.eazyviewdemo;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pkh.eazyview.lazyviewreplica.EazyView;
import com.pkh.eazyview.lazyviewreplica.EazyViewListener;
import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;
import com.pkh.eazyview.util.EazyViewUtil.ViewSize;
import com.pkh.eazyview.util.OptionView;

public class EazyViewDemoActivity extends FragmentActivity {
	/**
	 * creating id to the views
	 */
	 final int GNU_id=0;
	 final int OpenSource_id=1;
	 final int F_Droid_id=2;
	 final int Freedom_id=3;
	 final int About_id=4;
	
	/*
	 * view pager object
	 */
	public static ViewPager viewPager;
	public static Context CONTEXT;
	/**
	 * PagerAdapter object
	 */
	PagerAdapter mAdapter;
	/**
	 * Creating instance for EazyView Library
	 */
	static EazyView mEazyView;
	static ConnectivityManager cm ;
	
	ImageView mHelpImage;
	SharedPreferences preference;
	private String prefName="eazyView";
	private String IS_FIRST_VIEW_KEY="IS_FIRST_VIEW";
	private boolean isFirstView=false;
		       
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_page_holder);
	    CONTEXT=getApplicationContext();
	    cm=  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    /**
         * inititaing the view helper
         */
	    mHelpImage=(ImageView) findViewById(R.id.helpTouch);
        
	    /*
	     * initiating the pager
	     */
	    // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
       
       
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(!isOnline())
				{
					viewPager.setCurrentItem(0);
					Toast.makeText(getApplicationContext(), "Please Turn on the Internet", 0).show();
				}
				
				
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
      
	    /**
	     * initiating eazy view
	     */
        initEazyView();
        if(mEazyView!=null){
        	creatingViewForEazyView(mEazyView);
        }
	    
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		 /**
         * initaitn preference for first touch(help view)
         */
        preference=getSharedPreferences(prefName,MODE_PRIVATE);
        
        isFirstView=preference.getBoolean(IS_FIRST_VIEW_KEY, true);
        if(isFirstView){
        	mHelpImage.setVisibility(ViewGroup.VISIBLE);
        	Editor editor=preference.edit();
        	editor.putBoolean(IS_FIRST_VIEW_KEY, false);
        	editor.commit();
        }
	}
	/**
	 * static function to get callback for fragment
	 * @param 
	 */
	public static void OnButtonSelected(int id){
		switch (id) {
		case R.id.wordpressTuto:
			if(isOnline()){
			viewPager.setCurrentItem(1);
			}else{
				Toast.makeText(CONTEXT, "Please Turn on the Internet", 0).show();
			}
			break;
		case R.id.githubSrc:
			if(isOnline()){
			viewPager.setCurrentItem(2);
			}else{
				Toast.makeText(CONTEXT, "Please Turn on the Internet", 0).show();
			}
			break;
		case R.id.launchEazyView:
			
			mEazyView.openView();
			if(isOnline()){
				viewPager.setCurrentItem(3);
				}else{
					Toast.makeText(CONTEXT, "Please Turn on the Internet", 0).show();
				}
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * handling back button
	 */
	public void onBackPressed() {
		int currentFragPos=viewPager.getCurrentItem();
		if(currentFragPos!=0){
			viewPager.setCurrentItem(0);
			
		}else{
			finish();
		}
	};
	
	/**
	 * showing dialog while clicking the views
	 */
	AlertDialog mDialog;
	
	private void showOptionDialog(int idTitle){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(idTitle));
		
		builder.setPositiveButton("OK", null);
		
		
		switch (idTitle) {
		case R.string.gnu_title:
			builder.setMessage(getResources().getString(R.string.gnu_message));
			break;
		case R.string.opensource_titile:
			builder.setMessage(getResources().getString(R.string.opensource_message));
			break;
		case R.string.freedom_title:
			builder.setMessage(getResources().getString(R.string.freedom_message));
			break;
		case R.string.fdroid_title:
			builder.setMessage(getResources().getString(R.string.fdroid_message));
			break;
		case R.string.about_title:
			builder.setMessage(getResources().getString(R.string.about_message));
			break;

		default:
			break;
		}
		
		mDialog = builder.create();
		mDialog.show();
	}
	
	/**
	 * creating the option for the grid
	 * @param eazyView
	 */
	private void creatingViewForEazyView(EazyView eazyView) {
		ArrayList<OptionView> viewList=new ArrayList<OptionView>();
		
		  OptionView homeOptions=new OptionView(getApplicationContext());
		    homeOptions.setViewId(R.drawable.home_icon);
		    homeOptions.setImage(R.drawable.home_icon);
		    homeOptions.setOptionHolderBorderColor(Color.WHITE);
		    homeOptions.setOptionHolderBackgroundColor(Color.YELLOW);
		    homeOptions.setBorderWidth(0);
		    viewList.add(homeOptions);
		    
		    OptionView wordpresOption=new OptionView(getApplicationContext());
		    wordpresOption.setViewId(R.drawable.wordpress_icon);
		    wordpresOption.setImage(R.drawable.wordpress_icon);
		    wordpresOption.setOptionHolderBorderColor(Color.WHITE);;
		    wordpresOption.setOptionHolderBackgroundColor(Color.CYAN);
		    wordpresOption.setBorderWidth(1f);
		    viewList.add(wordpresOption);
		    
		    OptionView githubOption=new OptionView(getApplicationContext());
		    githubOption.setViewId(R.drawable.github_icon);
		    githubOption.setImage(R.drawable.github_icon);
		    githubOption.setOptionHolderBorderColor(Color.WHITE);
		    githubOption.setBorderWidth(0.5f);
		    githubOption.setOptionHolderBackgroundColor(Color.BLUE);
		    viewList.add(githubOption);
		
		    /**
		     * adding option to the view for text 
		     */
		    OptionView gnuOptions=new OptionView(getApplicationContext());
		    gnuOptions.setViewId(GNU_id);
		    gnuOptions.setText("GNU");
		    gnuOptions.setTextColor(Color.GREEN);
		    gnuOptions.setTextSize(0.7f);
		    gnuOptions.setOptionHolderBorderColor(Color.GREEN);
		    gnuOptions.setOptionHolderBackgroundColor(Color.BLACK);
		    viewList.add(gnuOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView openSourceOptions=new OptionView(getApplicationContext());
		    openSourceOptions.setViewId(OpenSource_id);
		    openSourceOptions.setText("OS");
		    openSourceOptions.setTextColor(Color.WHITE);
		    openSourceOptions.setTextSize(0.8f);
		    openSourceOptions.setOptionHolderBorderColor(Color.WHITE);
		    openSourceOptions.setOptionHolderBackgroundColor(Color.BLUE);
		    viewList.add(openSourceOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView freedomOptions=new OptionView(getApplicationContext());
		    freedomOptions.setViewId(Freedom_id);
		    freedomOptions.setText("Fdm");
		    freedomOptions.setTextColor(Color.YELLOW);
		    freedomOptions.setTextSize(0.9f);
		    freedomOptions.setOptionHolderBorderColor(Color.RED);
		    freedomOptions.setBorderWidth(0.1f);
		    freedomOptions.setOptionHolderBackgroundColor(Color.DKGRAY);
		    viewList.add(freedomOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView fdroidOptions=new OptionView(getApplicationContext());
		    fdroidOptions.setViewId(F_Droid_id);
		    fdroidOptions.setText("FDr");
		    fdroidOptions.setTextColor(Color.BLUE);
		    fdroidOptions.setTextSize(1);
		    fdroidOptions.setOptionHolderBorderColor(Color.BLUE);
		    fdroidOptions.setOptionHolderBackgroundColor(Color.WHITE);
		    viewList.add(fdroidOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView sorryOptions=new OptionView(getApplicationContext());
		    sorryOptions.setViewId(About_id);
		    sorryOptions.setText("i");
		    sorryOptions.setTextColor(Color.WHITE);
		    sorryOptions.setTextSize(1.5f);
		    sorryOptions.setOptionHolderBorderColor(Color.WHITE);
		    sorryOptions.setOptionHolderBackgroundColor(Color.BLUE);
		    viewList.add(sorryOptions);
		    
		    
		    /**
		     * 
		     * Adding options to QUADRANT 2 (II)
		     * ----------------------------------
		     * ----------------------------------
		     * ----------------------------------
		     * ----------------------------------
		     */
		    /**
		     * adding 4 option to the qUADRAND II  view with "-" text in a loop
		     */
		    for(int i=0;i<4;i++){
			    OptionView simple=new OptionView(getApplicationContext());
			    simple.setQuodrant(QUODRANT.TWO);
			    simple.setOptionHolderBackgroundColor(Color.GRAY);
			    if(i%2==0){
			    	if(i==0){

					    simple.setOptionHolderBackgroundColor(Color.BLUE);
					    simple.setBorderWidth(0);
			    	}
			    	simple.setOptionHolderBorderColor(Color.BLUE);
			    }else if(i%3==0){
			    	simple.setOptionHolderBorderColor(Color.RED);
			    }
			    simple.setViewId(-1);
			    simple.setText("DmY");
			    simple.setTextColor(Color.WHITE);
			    simple.setTextSize(1);
			    viewList.add(simple);
		    }

		/**
		 * we can only add a list to view library, and need to mention what is size of the view
		 */
		
		try {
			/**
			 *NOte: viewList size is less than the second parameter "ViewSize" all the views will be empty 
			 */
			eazyView.addViews(viewList, ViewSize.TWELVE);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	/*
	 * initiating EazyView Libarary
	 */
	private void initEazyView() {
		 
	    /**
	     * instance for eazyview
	     */
	    mEazyView=new EazyView(this, new EazyViewListener() {
			
			@Override
			public void onclick(int id) {
				switch (id) {
				case R.drawable.home_icon:
					if(isOnline()){
						viewPager.setCurrentItem(0);
						}else{
							Toast.makeText(CONTEXT, "Please Turn on the Internet", 0).show();
						}
					break;
				case R.drawable.github_icon:
					if(isOnline()){
						viewPager.setCurrentItem(2);
						}else{
							Toast.makeText(CONTEXT, "Please Turn on the Internet", 0).show();
						}
					break;
				case R.drawable.wordpress_icon:
					if(isOnline()){
						viewPager.setCurrentItem(1);
						}else{
							Toast.makeText(CONTEXT, "Please Turn on the Internet", 0).show();
						}
					break;
				case GNU_id:
					showOptionDialog(R.string.gnu_title);
					break;
				case OpenSource_id:
					showOptionDialog(R.string.opensource_titile);
					break;
				case F_Droid_id:
					showOptionDialog(R.string.fdroid_title);
					break;
				case Freedom_id:
					showOptionDialog(R.string.freedom_title);
					break;
				case About_id:
					showOptionDialog(R.string.about_title);
					break;

				default:
					break;
				}

			}
			
			@Override
			public void onViewOpend() {
				mHelpImage.setVisibility(ViewGroup.GONE);
				 preference=getSharedPreferences(prefName,MODE_PRIVATE);
			        
			        isFirstView=preference.getBoolean(IS_FIRST_VIEW_KEY, true);
			        if(isFirstView){
			        	mHelpImage.setVisibility(ViewGroup.VISIBLE);
			        	Editor editor=preference.edit();
			        	editor.putBoolean(IS_FIRST_VIEW_KEY, false);
			        	editor.commit();
			        }
				
			}
			
			@Override
			public void onViewClosed() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingView(QUODRANT arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    /**
	     * adding icon for launcher and size for the launcher
	     */
	    mEazyView.setLauncherBtnDrawable(R.drawable.setting_open	, R.drawable.setting_close , 10);
	    mEazyView.attachEazyViewToActivity();
	}
    
/**
 * check mobile is in oline
 */
	public static boolean isOnline(){
		    return cm.getActiveNetworkInfo() != null && 
		       cm.getActiveNetworkInfo().isConnectedOrConnecting();
		
	}
	
	
	

/**
 * creating adapter for view pager
 * @author pkh
 *
 */
class PagerAdapter extends FragmentPagerAdapter{

	public PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Home fragment activity
            return new HomeFragment();
        case 1:
            // wordpres tutorial fragment activity
        	
        		return new WordPressFragment();
        
        	
        case 2:
            // Github source code fragment activity
        	
        		return new GitHubFragment();
        case 3:
        	
        	    return new AdvFragment();
        	
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
	
}
	
}


