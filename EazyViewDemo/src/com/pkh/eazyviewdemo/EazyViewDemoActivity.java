package com.pkh.eazyviewdemo;

import java.util.ArrayList;

import com.pkh.eazyview.lazyviewreplica.EazyView;
import com.pkh.eazyview.lazyviewreplica.EazyViewListener;
import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;
import com.pkh.eazyview.util.EazyViewUtil.ViewSize;
import com.pkh.eazyview.util.OptionView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;

public class EazyViewDemoActivity extends FragmentActivity {

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
		       
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_page_holder);
	    CONTEXT=getApplicationContext();
	    cm=  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
			break;
		default:
			break;
		}
		
	}
	
	private void creatingViewForEazyView(EazyView eazyView) {
		ArrayList<OptionView> viewList=new ArrayList<OptionView>();
		
		  OptionView homeOptions=new OptionView(getApplicationContext());
		    homeOptions.setViewId(R.drawable.home_icon);
		    homeOptions.setImage(R.drawable.home_icon);
		    homeOptions.setOptionHolderBackgroundColor(Color.YELLOW);
		    viewList.add(homeOptions);
		    
		    OptionView wordpresOption=new OptionView(getApplicationContext());
		    wordpresOption.setViewId(R.drawable.wordpress_icon);
		    wordpresOption.setImage(R.drawable.wordpress_icon);
		    wordpresOption.setOptionHolderBackgroundColor(Color.CYAN);
		    viewList.add(wordpresOption);
		    
		    OptionView githubOption=new OptionView(getApplicationContext());
		    githubOption.setViewId(R.drawable.github_icon);
		    githubOption.setImage(R.drawable.github_icon);
		    githubOption.setOptionHolderBackgroundColor(Color.BLUE);
		    viewList.add(githubOption);
		
		    /**
		     * adding option to the view for text 
		     */
		    OptionView gnuOptions=new OptionView(getApplicationContext());
		    gnuOptions.setViewId(Utility.GNU_id);
		    gnuOptions.setText("GNU");
		    gnuOptions.setTextColor(Color.GREEN);
		    gnuOptions.setTextSize(50);
		    gnuOptions.setOptionHolderBackgroundColor(Color.BLACK);
		    viewList.add(gnuOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView openSourceOptions=new OptionView(getApplicationContext());
		    openSourceOptions.setViewId(Utility.OpenSource_id);
		    openSourceOptions.setText("OS");
		    openSourceOptions.setTextColor(Color.WHITE);
		    openSourceOptions.setTextSize(50);
		    openSourceOptions.setOptionHolderBackgroundColor(Color.BLUE);
		    viewList.add(openSourceOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView freedomOptions=new OptionView(getApplicationContext());
		    freedomOptions.setViewId(Utility.Freedom_id);
		    freedomOptions.setText("Fdm");
		    freedomOptions.setTextColor(Color.YELLOW);
		    freedomOptions.setTextSize(50);
		    freedomOptions.setOptionHolderBackgroundColor(Color.DKGRAY);
		    viewList.add(freedomOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView fdroidOptions=new OptionView(getApplicationContext());
		    fdroidOptions.setViewId(Utility.F_Droid_id);
		    fdroidOptions.setText("FDr");
		    fdroidOptions.setTextColor(Color.BLUE);
		    fdroidOptions.setTextSize(50);
		    fdroidOptions.setOptionHolderBackgroundColor(Color.WHITE);
		    viewList.add(fdroidOptions);
		    
		    /**
		     * adding option to the view for text 
		     */
		    OptionView sorryOptions=new OptionView(getApplicationContext());
		    sorryOptions.setViewId(Utility.SorryAd_id);
		    sorryOptions.setText("SRY");
		    sorryOptions.setTextColor(Color.RED);
		    sorryOptions.setTextSize(70);
		    sorryOptions.setOptionHolderBackgroundColor(Color.BLACK);
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
			    simple.setViewId(-1);
			    simple.setText("DmY");
			    simple.setTextColor(Color.WHITE);
			    simple.setTextSize(60);
			    simple.setOptionHolderBackgroundColor(Color.GRAY);
			    viewList.add(simple);
		    }

		/**
		 * we can only add a list to view library, and need to mention what is size of the view
		 */
		
		try {
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
			public void onclick(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onViewOpend() {
				// TODO Auto-generated method stub
				
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
	    mEazyView.setLauncherBtnDrawable(R.drawable.setting_open	, R.drawable.setting_close , 100);
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
        	
        	
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
	
}
	
}


