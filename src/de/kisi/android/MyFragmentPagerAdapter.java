package de.kisi.android;

import java.util.List;

import de.kisi.android.model.Place;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	int PAGE_COUNT;
	private List<Fragment> fragments;
	private KisiMain activity;

	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, KisiMain activity) {
		super(fm);
		this.fragments = fragments;
		this.activity = activity;
		PAGE_COUNT = fragments.size();
	}

	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int position) {

		return this.fragments.get(position);
	}

	/** Returns the number of pages */
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
	
	@Override
	public CharSequence getPageTitle(int num) {
		//int location_id = getItem(num).getArguments().getInt("location_id");
		Place l = activity.locations.valueAt(num);
		return l.getName();
	}
}