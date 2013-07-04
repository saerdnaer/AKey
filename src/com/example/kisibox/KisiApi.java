package com.example.kisibox;

import com.manavo.rest.RestApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;


public class KisiApi extends RestApi {
	
	private SharedPreferences settings;

	public KisiApi(Activity activity) {
		super(activity);
		
		this.settings = getSharedPreferences("Config", MODE_PRIVATE);
		String authToken = this.settings.getString("authToken", "");

		this.BASE_URL = "https://kisi.de/";
		this.urlSuffix = ".json" + ( !authToken.isEmpty() ? "?auth_token=" + authToken : "" );
		this.rest.setHost("kisi.de");
		//this.rest.setPort(80);
		this.setUserAgent("AKey");

		this.acceptAllSslCertificates();
	}

	// return class instance so we can chain calls
	public KisiApi authorize(String email, String token) {
		// email acts as the username and token as the password of the basic auth
		this.rest.authorize(email, token);
		return this;
	}
	
	private String appendAuthToken(String url) {
		String authToken = settings.getString("authToken", "");
		if ( authToken.isEmpty() ) {
			Log.d("error", "Auth token empty");
			return url;
		}
		return url + (url.contains("?") ? "&" : "?") + "auth_token=" + authToken;
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
