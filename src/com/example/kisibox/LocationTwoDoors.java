package com.example.kisibox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LocationTwoDoors extends Fragment {


	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.locationtwodoors, container, false);

		final Button buttonOne = (Button) mRelativeLayout
				.findViewById(R.id.buttonTwoDoorOne);
		
		final Button buttonTwo = (Button) mRelativeLayout
				.findViewById(R.id.buttonTwoDoorTwo);
		

		
		OnClickListener listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == buttonOne){
					Log.d("pressed","opening door one");
				}
				if(v == buttonTwo){
					Log.d("pressed","opening door two");
				}
			}
			
			
		};

		buttonOne.setOnClickListener(listener);
		buttonTwo.setOnClickListener(listener);

		return mRelativeLayout;
	}
}