package de.kisi.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationTwoDoors extends Fragment {

	// TODO: change later to Location/Gate class
	static LocationTwoDoors newInstance(String name, String streetName,
			String streetNumber, String gate1, String gate2) {
		LocationTwoDoors f = new LocationTwoDoors();

		Bundle args = new Bundle();
		args.putString("Name", name);
		args.putString("StreetName", streetName);
		args.putString("StreetNumber", streetNumber);
		args.putString("Door1", gate1);
		args.putString("Door2", gate2);
		f.setArguments(args);

		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.locationtwodoors, container, false);

		final Button buttonOne = (Button) mRelativeLayout
				.findViewById(R.id.buttonTwoDoorOne);

		buttonOne.setText(getArguments().getString("Door1"));

		final Button buttonTwo = (Button) mRelativeLayout
				.findViewById(R.id.buttonTwoDoorTwo);

		buttonTwo.setText(getArguments().getString("Door2"));

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO implement opening door
				if (v == buttonOne) {
					Log.d("pressed", "opening door one");
				}
				if (v == buttonTwo) {
					Log.d("pressed", "opening door two");
				}
			}

		};

		buttonOne.setOnClickListener(listener);
		buttonTwo.setOnClickListener(listener);

		TextView adress = (TextView) mRelativeLayout
				.findViewById(R.id.textViewTwoDoors);
		adress.setText(getArguments().getString("StreetName") + " "
				+ getArguments().getString("StreetNumber"));

		return mRelativeLayout;
	}
}