package de.kisi.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.manavo.rest.RestApi;

import android.app.Activity;



public class KisiApi extends RestApi {
	
	private static String authToken;
	private static int user_id;

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
	public static String getAuthToken() {
		return KisiApi.authToken;
	}
	public static void setUserId(int user_id) {
		KisiApi.user_id = user_id;
	}
	public static int getUserId() {
		return KisiApi.user_id;
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
	
	@Override 
	public void onStatusCodeError(int code, String data) {
		if (this.errorCallback == null && data.startsWith("{\"error\":") ) {
			try {
				JSONObject data_json = new JSONObject(data);
				data = data_json.getString("error");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		super.onStatusCodeError(code, data);
	}
}
