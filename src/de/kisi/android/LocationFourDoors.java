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

public class LocationFourDoors extends Fragment {

	// TODO: change later to Location/Gate class
	static LocationFourDoors newInstance(String name, String streetName,
			String streetNumber, String gate1, String gate2, String gate3,
			String gate4) {
		LocationFourDoors f = new LocationFourDoors();

		Bundle args = new Bundle();
		args.putString("Name", name);
		args.putString("StreetName", streetName);
		args.putString("StreetNumber", streetNumber);
		args.putString("Door1", gate1);
		args.putString("Door2", gate2);
		args.putString("Door3", gate3);
		args.putString("Door4", gate4);
		f.setArguments(args);

		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.locationfourdoors, container, false);

		final Button buttonOne = (Button) mRelativeLayout
				.findViewById(R.id.buttonFourDoorOne);
		
		buttonOne.setText(getArguments().getString("Door1"));

		final Button buttonTwo = (Button) mRelativeLayout
				.findViewById(R.id.buttonFourDoorTwo);

		buttonTwo.setText(getArguments().getString("Door2"));

		
		final Button buttonThree = (Button) mRelativeLayout
				.findViewById(R.id.buttonFourDoorThree);
		
		buttonThree.setText(getArguments().getString("Door3"));


		final Button buttonFour = (Button) mRelativeLayout
				.findViewById(R.id.buttonFourDoorFour);
		
		buttonFour.setText(getArguments().getString("Door4"));


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
				if (v == buttonThree) {
					Log.d("pressed", "opening door three");
				}
				if (v == buttonFour) {
					Log.d("pressed", "opening door four");
				}
			}

		};

		buttonOne.setOnClickListener(listener);
		buttonTwo.setOnClickListener(listener);
		buttonThree.setOnClickListener(listener);
		buttonFour.setOnClickListener(listener);
		
		TextView adress = (TextView) mRelativeLayout.findViewById(R.id.textViewFourDoors);
		adress.setText(getArguments().getString("StreetName")+" "+getArguments().getString("StreetNumber"));

		return mRelativeLayout;
	}
}
