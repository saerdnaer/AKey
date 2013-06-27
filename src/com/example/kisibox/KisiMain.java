package com.example.kisibox;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.TextView;

public class KisiMain extends FragmentActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.kisi_main);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

		TextView title = (TextView) findViewById(R.id.title);

		title.setText("Your Locations");

		initializePager();

	}

	public void initializePager() {
		//TODO: change from Test to real Data
		String street = "Musterstraße";
		String housenumber = "5";
		String door1 = "Front Door";
		String door2 = "Apartment Door";
		String door3 = "Garage Door";
		String door4 = "Cellar Door";

		List<Fragment> fragments = new Vector<Fragment>(); 
		fragments.add(LocationOneDoor.newInstance("Home",street,housenumber,door1));
		
		
		fragments.add(LocationTwoDoors.newInstance("Home",street,housenumber,door1,door2));
		fragments.add(LocationThreeDoors.newInstance("Home",street,housenumber,door1,door2,door3));	
		fragments.add(LocationFourDoors.newInstance("Home",street,housenumber,door1,door2,door3,door4));		
		fragments.add(LocationThreeDoors.newInstance("Mustafa","Römerstraße","10","Front Door","Elevator","Apartment Door"));
		
		FragmentManager fm = getSupportFragmentManager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm,
				fragments);
		pager.setAdapter(pagerAdapter);

	}
}
