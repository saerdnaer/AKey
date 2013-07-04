package com.example.kisibox;

import java.util.List;
import java.util.Vector;

import android.content.Intent;
import android.content.SharedPreferences;
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

	@Override
	public void onPause() { //sends user back to Login Screen if he didn't choose remember me 
		SharedPreferences settings = getSharedPreferences("Config",
				MODE_PRIVATE);
		if (!settings.getBoolean("saved", false)) {
			Intent loginScreen = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(loginScreen);
		}
		super.onPause();
	}

	public void initializePager() {
		// TODO: change from Test to real Data

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(LocationOneDoor.newInstance("Home", "Römerstraße", "17",
				"Front Door"));

		fragments.add(LocationTwoDoors.newInstance("Meier", "Hauptstraße", "5",
				"Front Door", "Appartment Door"));
		fragments.add(LocationThreeDoors.newInstance("Müller", "Dorfstraße",
				"23", "Front Door", "Garage Door", "Cellar Door"));
		fragments.add(LocationFourDoors
				.newInstance("Uni", "Boltzmannstraße", "15", "Front Door",
						"Library", "Room 02.07.23", "Room 00.09.11"));

		FragmentManager fm = getSupportFragmentManager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm,
				fragments);
		pager.setAdapter(pagerAdapter);

	}
}
