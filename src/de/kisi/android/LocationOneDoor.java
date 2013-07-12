package de.kisi.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationOneDoor extends Fragment {

	// TODO: change later to Location/Gate class
	static LocationOneDoor newInstance(String name, String streetName,
			String streetNumber, String gate) {
		LocationOneDoor f = new LocationOneDoor();

		Bundle args = new Bundle();
		args.putString("Name", name);
		args.putString("StreetName", streetName);
		args.putString("StreetNumber", streetNumber);
		args.putString("Door", gate);
		f.setArguments(args);

		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.locationonedoor, container, false);

		Button mButton = (Button) mRelativeLayout
				.findViewById(R.id.buttonOneDoorOne);
		mButton.setText(getArguments().getString("Door"));
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO implement opening door
				Log.d("pressed", "opening door one");
			}
		});

		TextView adress = (TextView) mRelativeLayout
				.findViewById(R.id.textViewOneDoor);
		adress.setText(getArguments().getString("StreetName") + " "
				+ getArguments().getString("StreetNumber"));

		return mRelativeLayout;
	}
}
