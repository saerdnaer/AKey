package com.example.kisibox;

import java.util.List;

import org.json.JSONArray;

import com.example.kisibox.model.Gate;
import com.example.kisibox.model.Location;
import com.manavo.rest.RestCallback;

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

public class LocationViewFragment extends Fragment {

	private RelativeLayout layout;
	
	static LocationViewFragment newInstance(int location_id) {
		// Fragments must not have a custom constructor 
		LocationViewFragment f = new LocationViewFragment();

		Bundle args = new Bundle();
		args.putInt("location_id", location_id);
		f.setArguments(args);

		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		final Location l = ((KisiMain)getActivity()).locations.get(getArguments().getInt("location_id"));
		
		layout = (RelativeLayout) inflater.inflate(R.layout.locationtwodoors, container, false);

		TextView adress = (TextView) layout.findViewById(R.id.textViewTwoDoors);
		adress.setText(l.getAddress());

		KisiApi api = new KisiApi(this.getActivity());

		api.setCallback(new RestCallback() {
			public void success(Object obj) {
				JSONArray data = (JSONArray)obj;

				l.setGates(data);
				setupButtons(l.getGates());
			}

		});
		api.get("locations/" + String.valueOf(l.getId()) + "/gates");
		
		return layout;
	}
	
	public void setupButtons(List<Gate> gates) {
		int[] buttons = {R.id.buttonTwoDoorOne,R.id.buttonTwoDoorTwo};
		
		int i = 0;
		for ( final Gate gate : gates ) {
			final Button button = (Button) layout.findViewById(buttons[i++]);
			button.setText(getArguments().getString(gate.getName()));
			
			button.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("pressed", "opening door " + String.valueOf(gate.getName()));
				}
			});
		}
	}
}