package de.kisi.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.manavo.rest.RestApi;

import android.app.Activity;



public class KisiApi extends RestApi {
	
	private static String authToken;

	public KisiApi(Activity activity) {
		super(activity);

		this.BASE_URL = "https://www.kisi.de/";
		this.urlSuffix = ".json" + ( KisiApi.authToken != null ? "?auth_token=" + KisiApi.authToken : "" );
		this.rest.setHost("www.kisi.de");
		this.rest.setPort(443);
		this.setUserAgent("AKey");
		this.rest.setContentType("application/json");
	}

	public static void setAuthToken(String token) {
		KisiApi.authToken = token;
	}
	
	// return class instance so we can chain calls
	public KisiApi authorize(String email, String password) {
		try {
			JSONObject data = new JSONObject();
			data.put("email", email);
			data.put("password", password);
			this.addParameter("user", (Object) data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return this;
	}

	@Override // to make method public
	public void get(String url) {
		super.get(url);
	}
	
	@Override 
	public void post(String url) {
		super.post(url);
	}
}