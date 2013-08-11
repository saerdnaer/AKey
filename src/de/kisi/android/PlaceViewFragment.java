package de.kisi.android;

import java.util.List;

import org.json.JSONArray;

import com.manavo.rest.RestCallback;

import de.kisi.android.model.Lock;
import de.kisi.android.model.Place;

import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.TextView;

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
		int[] texts = {R.id.placeOneText,R.id.placeTwoText,R.id.placeThreeText};
		
		Typeface font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(),"Roboto-Light.ttf"); 

		int i = 0;
		for ( final Lock lock : locks ) {
			if ( i >= buttons.length ) {
				Log.d("waring", "more locks then buttons!");
				break;
			}

			final Button button = (Button) layout.findViewById(buttons[i]);
			final TextView currentText = (TextView) layout.findViewById(texts[i]);
			currentText.setText(lock.getName());
			currentText.setTypeface(font);
			currentText.setVisibility(View.VISIBLE);
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
							buttonToUnlock(button, lock , currentText);

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
			TextView text = (TextView) layout.findViewById(texts[i]);
			button.setVisibility(View.GONE);
			text.setVisibility(View.GONE);
			

		}
	}
	
	public void buttonToUnlock(Button button, Lock lock, TextView text){
		//save button design
		final TextView currentText = text;
		final Drawable currentBackground = button.getBackground();
		final Button currentButton = button;
		
		final float density = getActivity().getResources().getDisplayMetrics().density;
		final int shift = (int)(43*density);
		
		//change to unlocked design
		
		currentText.setTextColor(Color.parseColor("#5fe100"));
		currentButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.unlocked));
		currentButton.setPadding(currentButton.getPaddingLeft()+shift, 0, 0, 0);
		// TODO localize?
		currentButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kisi_lock_open2,0, 0, 0);
		
		//disable click
		currentButton.setClickable(false);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
			public void run(){
				
				//after delay back to old design re-enable click
				currentText.setTextColor(Color.parseColor("#FFFFFF"));
				currentButton.setBackgroundDrawable(currentBackground);
				currentButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kisi_lock,0, 0, 0);
				currentButton.setPadding(currentButton.getPaddingLeft()-shift, 0, 0, 0);
				currentButton.setClickable(true);

			}
		}, delay);
		
	}
}