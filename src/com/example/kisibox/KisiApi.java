package com.example.kisibox;

import com.manavo.rest.RestApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;


public class KisiApi extends RestApi {

	public KisiApi(Activity activity) {
		super(activity);
		
		SharedPreferences settings = getSharedPreferences("Config", MODE_PRIVATE);
		String authToken = settings.getString("authToken", "");

		this.BASE_URL = "https://kisi.de/";
		this.urlSuffix = ".json" + ( KisiApi.authToken != null ? "?auth_token=" + KisiApi.authToken : "" );
		this.rest.setHost("kisi.de");
		//this.rest.setPort(80);
		this.setUserAgent("AKey");

		this.acceptAllSslCertificates();
	public static void setAuthToken(String token) {
		KisiApi.authToken = token;
	}
	// return class instance so we can chain calls
	public KisiApi authorize(String email, String password) {
		//addParameter("user", JSONObject("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"));
		addParameter("user", "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}");
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
