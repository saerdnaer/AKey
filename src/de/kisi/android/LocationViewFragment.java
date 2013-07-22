package de.kisi.android;

import java.util.List;

import org.json.JSONArray;

import com.manavo.rest.RestCallback;

import de.kisi.android.model.Lock;
import de.kisi.android.model.Location;

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
import android.widget.Toast;

public class LocationViewFragment extends Fragment {

	private RelativeLayout layout;
	
	static LocationViewFragment newInstance(int index) {
		// Fragments must not have a custom constructor 
		LocationViewFragment f = new LocationViewFragment();

		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		int index = getArguments().getInt("index");
		final Location l = ((KisiMain)getActivity()).locations.valueAt(index);
		
		layout = (RelativeLayout) inflater.inflate(R.layout.locationtwodoors, container, false);

		TextView adress = (TextView) layout.findViewById(R.id.textViewTwoDoors);
		adress.setText(l.getAddress());

		if ( l.getLocks() == null ) {
			KisiApi api = new KisiApi(this.getActivity());
	
			api.setCallback(new RestCallback() {
				public void success(Object obj) {
					JSONArray data = (JSONArray)obj;
	
					l.setLocks(data);
					setupButtons(l.getLocks());
				}
	
			});
			api.setLoadingMessage(null);
			api.get("locations/" + String.valueOf(l.getId()) + "/locks");
		}
		else {
			setupButtons(l.getLocks());
		}
		
		return layout;
	}
	
	public void setupButtons(List<Lock> locks) {
		int[] buttons = {R.id.buttonTwoDoorOne, R.id.buttonTwoDoorTwo};
		
		int i = 0;
		for ( final Lock lock : locks ) {
			if ( i >= buttons.length ) {
				Log.d("waring", "more locks then buttons!");
				break;
			}
			final Button button = (Button) layout.findViewById(buttons[i++]);
			button.setText("Unlock " + lock.getName());
			button.setVisibility(View.VISIBLE);
			
			button.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("pressed", "opening door " + String.valueOf(lock.getName()));
					KisiApi api = new KisiApi(getActivity());

					api.setCallback(new RestCallback() {
						public void success(Object obj) {
							Toast.makeText(getActivity(), "Lock was opened successfully", Toast.LENGTH_LONG).show();
						}

					});
					api.setLoadingMessage("Opening lock...");
					api.post("locations/" + String.valueOf(lock.getLocationId()) + "/locks/" + String.valueOf(lock.getId()) + "/access" );
				}
			});
		}
		// set unused buttons to gone, so the automatic layout works
		for ( ; i < buttons.length; i++) {
			Button button = (Button) layout.findViewById(buttons[i]);
			button.setVisibility(View.GONE);
		}
	}
}