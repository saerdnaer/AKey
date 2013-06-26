package com.example.kisibox;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class KisiMain extends FragmentActivity{



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.kisi_main);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
		
		TextView title = (TextView) findViewById(R.id.title);
		
		title.setText("Your Locations");
		
		initializePager();


	}
	
	
	public void initializePager(){
		
		List<Fragment> fragments = new Vector<Fragment>(); //testing purpose
		
		fragments.add(Fragment.instantiate(this, LocationOneDoor.class.getName()));
		fragments.add(Fragment.instantiate(this, LocationTwoDoors.class.getName()));
		fragments.add(Fragment.instantiate(this, LocationThreeDoors.class.getName()));
		fragments.add(Fragment.instantiate(this, LocationFourDoors.class.getName()));
		
		FragmentManager fm = getSupportFragmentManager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);		
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm,fragments);
        pager.setAdapter(pagerAdapter);
		
	}
}
