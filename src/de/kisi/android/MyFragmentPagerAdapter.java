package de.kisi.android;

import java.util.List;

import de.kisi.android.model.Location;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

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
		//int location_id = getItem(num).getArguments().getInt("location_id");
		// TODO is there a nicer way to get the Activity? --Andi
		SparseArray<Location> locations = ((KisiMain) getItem(0).getActivity()).locations;
		//Location l = locations.get(location_id);
		Location l = locations.valueAt(num);
		return l.getName();
	}
}