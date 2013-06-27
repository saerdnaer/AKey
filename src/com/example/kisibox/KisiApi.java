package com.example.kisibox;

import com.manavo.rest.RestApi;
import android.app.Activity;


public class KisiApi extends RestApi {

	public KisiApi(Activity activity) {
		super(activity);

		this.BASE_URL = "https://kisi.de/";
		this.urlSuffix = ".json";
		this.rest.setHost("kisi.de");
		//this.rest.setPort(80);
		this.setUserAgent("AKey");

		this.acceptAllSslCertificates();

		Login loginData = ((MainActivity) activity).getLogin();
		this.authorize(loginData.getUserName(), loginData.getPasword());
	}

	// return SprinterApi so we can chain calls
	public KisiApi authorize(String email, String token) {
		// email acts as the username and token as the password of the basic auth
		this.rest.authorize(email, token);
		return this;
	}
}
