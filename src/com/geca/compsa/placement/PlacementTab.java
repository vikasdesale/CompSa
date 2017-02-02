package com.geca.compsa.placement;

import com.geca.compsa.R;
import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

@SuppressLint("NewApi") @SuppressWarnings("deprecation")
public class PlacementTab extends TabActivity implements OnTabChangeListener, OnMenuItemClickListener {
    /** Called when the activity is first created. */
	 TabHost tabHost;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placement);
        ImageView iv1=(ImageView) findViewById(R.id.imageView1);
        ImageView iv2=(ImageView) findViewById(R.id.imageView3);
        ImageView iv3=(ImageView) findViewById(R.id.imageView2);
        iv1.setBackgroundResource(R.anim.animation1);
        iv2.setBackgroundResource(R.anim.animation);
        iv3.setBackgroundResource(R.anim.animation2);
        AnimationDrawable anim1=(AnimationDrawable) iv1.getBackground();
        AnimationDrawable anim2=(AnimationDrawable) iv2.getBackground();
        AnimationDrawable anim3=(AnimationDrawable) iv3.getBackground();
            anim1.start();
            anim2.start();    
            anim3.start();
       tabHost = getTabHost();
        
        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("Placement Procedure");
        photospec.setIndicator("Placement Procedure", getResources().getDrawable(R.drawable.icon_photos_tab));
        Intent photosIntent = new Intent(this,PlacementProc.class);
        photospec.setContent(photosIntent);
        
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Main Recruiters");
        // setting Title and Icon for the Tab
        songspec.setIndicator("Main Recruiters", getResources().getDrawable(R.drawable.icon_songs_tab));
        Intent songsIntent = new Intent(this, MainRecruiters.class);
        songspec.setContent(songsIntent);
        
        // Tab for Videos
        TabSpec videospec = tabHost.newTabSpec("Placement Record");
        videospec.setIndicator("Placement Record", getResources().getDrawable(R.drawable.icon_videos_tab));
        Intent videosIntent = new Intent(this, PlacementR.class);
        videospec.setContent(videosIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(videospec); // Adding videos tab
        
        
   
		tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);

		tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);

		tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
		tabHost.setCurrentTab(0); // Default Selected Tab
		tabHost.setOnTabChangedListener(this);
		tabHost.getTabWidget().getChildAt(0)
		.setBackgroundResource(R.drawable.tab_selector);
	
		}

	
   
    
  

@Override
public void onTabChanged(String tabId) {
	// TODO Auto-generated method stub
	if (tabId.equals("Placement Procedure")) {
		tabHost.getTabWidget().getChildAt(0)
				.setBackgroundResource(R.drawable.tab_selector);
		tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
		tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
	} else if (tabId.equals("Main Recruiters")) {
		tabHost.getTabWidget().getChildAt(1)
				.setBackgroundResource(R.drawable.tab_selector);

		tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
		tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
	} else if (tabId.equals("Placement Record")) {
		tabHost.getTabWidget().getChildAt(2)
				.setBackgroundResource(R.drawable.tab_selector);
		tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
		tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
	}
}
public boolean onMenuItemClick(MenuItem item) {
	  switch(item.getItemId()){


      case R.id.item_about:
    	  Intent j11 =new Intent(PlacementTab.this, com.geca.compsa.about.AboutUsDev.class);
          startActivity(j11);
          break;

  }
  return true;
	}
}