package de.kisi.android;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.manavo.rest.RestCallback;

import de.kisi.android.model.Lock;
import de.kisi.android.model.Place;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

public class KisiMain extends FragmentActivity implements
		PopupMenu.OnMenuItemClickListener {

	public SparseArray<Place> locations;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.kisi_main);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

		initializePager();

	}

	public void showPopup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.settings, popup.getMenu());
		popup.setOnMenuItemClickListener(this);

		popup.show();
	}

	@Override
	public void onPause() { // sends user back to Login Screen if he didn't
							// choose remember me
		SharedPreferences settings = getSharedPreferences("Config",
				MODE_PRIVATE);
		if (!settings.getBoolean("saved", false)) {
			Intent loginScreen = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(loginScreen);
		}
		super.onPause();
	}

	public void initializePager() {
		updateLocations();
	}

	private void updateLocations() {
		KisiApi api = new KisiApi(this);

		api.setCallback(new RestCallback() {
			public void success(Object obj) {
				JSONArray data = (JSONArray) obj;

				setupView(data);
			}

		});
		api.get("places");

	}

	private void setupView(JSONArray locations_json) {

		List<Fragment> fragments = new Vector<Fragment>();
		locations = new SparseArray<Place>();

		try {
			for (int i = 0, j = 0; i < locations_json.length(); i++) {
				Place location = new Place(locations_json.getJSONObject(i));
				// The API returned some locations twice, so let's check if we
				// already have it or not
				if (locations.indexOfKey(location.getId()) < 0) {
					locations.put(location.getId(), location);
					fragments.add(PlaceViewFragment.newInstance(j++));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FragmentManager fm = getSupportFragmentManager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm,
				fragments);
		pager.setAdapter(pagerAdapter);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh:
			Log.d("refresh", "try to refresh");

			// TODO implement Refresh
			return true;

		case R.id.share:
			// TODO add view with form to select locks + mailadress
			ViewPager pager = (ViewPager) findViewById(R.id.pager);
			Place p = locations.valueAt(pager.getCurrentItem());
			
			if ( p.getOwnerId() != KisiApi.getUserId() ) {
				Toast.makeText(this, "Only the owner of a place can create new keys.", Toast.LENGTH_LONG).show();
				return false;
			}
			
			
			KisiApi api = new KisiApi(this);
			
			JSONArray lock_ids = new JSONArray();
			for ( Lock l : p.getLocks() ) {
				lock_ids.put(l.getId());
			}
			JSONObject key = new JSONObject();
			try {
				key.put("lock_ids", lock_ids);
				key.put("assignee_email", "johann.rottenfusser@gmx.net");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			api.addParameter("key", (Object) key);

			final Activity activity = this;
			
			api.setCallback(new RestCallback() {
				public void success(Object obj) {
					JSONObject data = (JSONObject) obj;
					try {
						Toast.makeText(activity, String.format("Key for %s was created successfully.", data.getString("assignee_email")), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					//Log.d("share", data.toString());
				}

			});
			api.post("places/" + String.valueOf(p.getId()) + "/keys");
			
			return true;

		}
		return false;

	}
}
