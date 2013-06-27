package com.example.kisibox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button loginButton;
	private EditText userNameField;
	private EditText paswordField;

	private SharedPreferences settings;
	private SharedPreferences.Editor editor;

	private Login loginData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_main);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

		loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(this);

		settings = getSharedPreferences("Config", MODE_PRIVATE);

		userNameField = (EditText) findViewById(R.id.editText1);

		paswordField = (EditText) findViewById(R.id.editText2);

	}

	@Override
	protected void onStart() {
		Login savedLogin = getLogin();

		if (!savedLogin.getUserName().equals("")) {
			userNameField.setText(savedLogin.getUserName());
			paswordField.setText(savedLogin.getPasword());
		}
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// user touched the login botton: gather all informations and send to next view

		String userName = userNameField.getText().toString(); // get Text of
		// EditTextfield

		String pasword = paswordField.getText().toString();

		loginData = new Login(userName, pasword);

		// Toast.makeText(this, userName+" "+pasword, Toast.LENGTH_LONG).show();

		CheckBox savePasword = (CheckBox) findViewById(R.id.checkBox1);

		if (savePasword.isChecked()) {
			Log.d("check", "saving");
			saveLogin(loginData);

		} else {
			Log.d("check", "deleting");
			deleteLogin();
		}

		login();

	}

	private void login() {
		Toast.makeText(this, "logging in", Toast.LENGTH_LONG).show();
		Intent mainScreen = new Intent(getApplicationContext(), KisiMain.class);
		startActivity(mainScreen);
	}

	private void deleteLogin() {
		editor = settings.edit();
		editor.remove("userName");
		editor.remove("pasword");
		editor.commit();
	}

	private void saveLogin(Login login) {
		editor = settings.edit();
		editor.putString("userName", login.getUserName());
		editor.putString("pasword", login.getPasword());
		editor.commit();
	}

	public Login getLogin() {
		if (loginData == null) {
			String tempUser = settings.getString("userName", "");
			String tempPw = settings.getString("pasword", "");

			loginData = new Login(tempUser, tempPw);
		}
		return loginData;
	}

}
