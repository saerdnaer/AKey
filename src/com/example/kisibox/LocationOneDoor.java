package com.example.kisibox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LocationOneDoor extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.locationonedoor, container, false);


		Button mButton = (Button) mRelativeLayout.findViewById(R.id.buttonOneDoorOne);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("pressed","opening door one");
			}
		});

		return mRelativeLayout;
	}
}
