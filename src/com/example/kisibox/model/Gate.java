package com.example.kisibox.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.example.kisibox.KisiApi;
import com.manavo.rest.RestCallback;

public class Gate {
	private int id;
	private String name;
	private int location_id;
	private String updated_at;
	
	public Gate(JSONObject json) {
		try {
			id = json.getInt("id");
			name = json.getString("name");
			location_id = json.getInt("location_id");
			updated_at = json.getString("updated_at");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getLocationId() {
		return location_id;
	}
	
	public boolean open(final Activity activity) {
		KisiApi api = new KisiApi(activity);

		api.setCallback(new RestCallback() {
			public void success(Object obj) {
				Toast.makeText(activity, "Gate was opened successfully", Toast.LENGTH_LONG).show();
			}

		});
		api.setLoadingMessage("Opening gate...");
		api.post("locations/" + String.valueOf(location_id) + "/gates/" + String.valueOf(id) + "/access" );
		return false;
	}

	public String getUpdatedAt() {
		return updated_at;
	}

}
