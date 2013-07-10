package com.example.kisibox;

import java.util.List;

import com.example.kisibox.model.Location;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	int PAGE_COUNT;
	private List<Fragment> fragments;

	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
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
		int location_id = getItem(num).getArguments().getInt("location_id");
		Location l = ((KisiMain) getItem(num).getActivity()).locations.get(location_id);
		return l.getName();
	}
}