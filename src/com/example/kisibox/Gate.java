package com.example.kisibox;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public boolean open() {
		// TODO Implement
		// call "https://kisi.de/locations/" + location_id + "/gates/" + id + "/access"
		return false;
	}

}
