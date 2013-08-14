package de.kisi.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class LogInfo extends Activity implements OnClickListener{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.loginfo);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.log_title);
		
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());

		//TODO change to Kisi Logpage
		webView.loadUrl("http://www.google.com");
		
		ImageButton backButton = (ImageButton) findViewById(R.id.back);
		
		backButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		this.finish();
		
	}
}