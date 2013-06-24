package com.example.kisibox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class KisiMain extends Activity{

	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.kisi_main);
	
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

	
	}
}
