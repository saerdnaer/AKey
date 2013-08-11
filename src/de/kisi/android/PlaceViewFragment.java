package de.kisi.android;

import java.util.List;

import org.json.JSONArray;

import com.manavo.rest.RestCallback;

import de.kisi.android.model.Lock;
import de.kisi.android.model.Place;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class PlaceViewFragment extends Fragment {

	private RelativeLayout layout;
	private final long delay = 5000;
	
	static PlaceViewFragment newInstance(int index) {
		// Fragments must not have a custom constructor 
		PlaceViewFragment f = new PlaceViewFragment();

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
		final Place l = ((KisiMain)getActivity()).locations.valueAt(index);
		
		layout = (RelativeLayout) inflater.inflate(R.layout.place_fragment, container, false);

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
			api.get("places/" + String.valueOf(l.getId()) + "/locks");
		}
		else {
			setupButtons(l.getLocks());
		}
		
		return layout;
	}
	
	public void setupButtons(List<Lock> locks) {
		int[] buttons = {R.id.buttonLockOne, R.id.buttonLockTwo, R.id.buttonLockThree};

		int i = 0;
		for ( final Lock lock : locks ) {
			if ( i >= buttons.length ) {
				Log.d("waring", "more locks then buttons!");
				break;
			}

			final Button button = (Button) layout.findViewById(buttons[i]);
			// TODO localize?
			button.setText("Unlock"+ "\n"+lock.getName());
			button.setVisibility(View.VISIBLE);
			i++;
			
			button.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					KisiApi api = new KisiApi(getActivity());

					api.setCallback(new RestCallback() {
						public void success(Object obj) {
							//Toast.makeText(getActivity(), "Lock was opened successfully", Toast.LENGTH_LONG).show();
							//change button design
							buttonToUnlock(button, lock);

						}

					});
					api.setLoadingMessage("Opening lock...");
					api.post("places/" + String.valueOf(lock.getPlaceId()) + "/locks/" + String.valueOf(lock.getId()) + "/access" );
				}
			});
		}
		// set unused buttons to gone, so the automatic layout works
		for ( ; i < buttons.length; i++) {
			Button button = (Button) layout.findViewById(buttons[i]);
			button.setVisibility(View.GONE);

		}
	}
	
	public void buttonToUnlock(Button button, Lock lock){
		//save button design
		final Drawable currentBackground = button.getBackground();
		final String currentString = (String) button.getText();
		final Button currentButton = button;
		
		//change to unlocked design
		currentButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.unlocked));
		// TODO localize?
		currentButton.setText("Unlocked " + lock.getName());
		currentButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.unlock, 0, 0, 0);
		
		//disable click
		currentButton.setClickable(false);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
			public void run(){
				
				//after delay back to old design re-enable click
				currentButton.setBackgroundDrawable(currentBackground);
				currentButton.setText(currentString);
				currentButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, 0, 0);
				currentButton.setClickable(true);

			}
		}, delay);
		
	}
}