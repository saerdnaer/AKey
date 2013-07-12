package de.kisi.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.manavo.rest.RestCallback;

import de.kisi.android.model.Login;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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

	private TextView newUser;
	private TextView forgotPw;
	private TextView slogan;

	private SharedPreferences settings;
	private SharedPreferences.Editor editor;

	private Login loginData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(this);
		
		loginButton.getBackground().setAlpha(185);
		

		settings = getSharedPreferences("Config", MODE_PRIVATE);

		userNameField = (EditText) findViewById(R.id.editText1);
		paswordField = (EditText) findViewById(R.id.editText2);
		paswordField.setTypeface(Typeface.DEFAULT);

		newUser = (TextView) findViewById(R.id.textView1);

		newUser.setText(Html.fromHtml("New? "
				+ "<a href=\"https://www.kisi.de/users/sign_up\">Get started on our website</a> "));
		newUser.setMovementMethod(LinkMovementMethod.getInstance());

		slogan = (TextView) findViewById(R.id.Slogan);
		Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		slogan.setTypeface(font);
		
		forgotPw = (TextView) findViewById(R.id.forgot);
		forgotPw.setText(Html.fromHtml("<a href=\"https://www.kisi.de/users/password/new\">Forgot your password?</a> "));
		forgotPw.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public void onBackPressed() { // sends App to Background if Backbutton is
									// pressed
									// prevents security issues
		moveTaskToBack(true);
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
		
		//Toast.makeText(this, "logging in", Toast.LENGTH_LONG).show();

		String email = userNameField.getText().toString(); // get Text of
		String password = paswordField.getText().toString();
		
		KisiApi api = new KisiApi(this);

		api.authorize(email, password);
		api.setLoadingMessage("Logging in...");
		
		final MainActivity activity = this;
		
		api.setCallback(new RestCallback() {
			public void success(Object obj) {
				JSONObject data = (JSONObject)obj;

				try {
					KisiApi.setAuthToken(data.getString("authentication_token"));
					Toast.makeText(activity, "Login successful", Toast.LENGTH_LONG).show();
					
					Intent mainScreen = new Intent(getApplicationContext(), KisiMain.class);
					startActivity(mainScreen);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// TODO set error handler

		loginData = new Login(email, password);

		// Toast.makeText(this, userName+" "+password, Toast.LENGTH_LONG).show();

		CheckBox savePassword = (CheckBox) findViewById(R.id.checkBox1);

		if (savePassword.isChecked()) {
			Log.d("check", "saving");
			saveLogin(loginData);

		} else {
			Log.d("check", "deleting");
			deleteLogin();
		}
		api.post("users/sign_in");
	}

	private void deleteLogin() {
		editor = settings.edit();
		//editor.remove("userName"); // (Maybe) temporary for debugging purpurses --Andi
		editor.remove("pasword");
		editor.remove("saved");
		editor.commit();
	}

	private void saveLogin(Login login) {
		editor = settings.edit();
		editor.putString("userName", login.getUserName());
		editor.putString("pasword", login.getPasword());
		editor.putBoolean("saved", true);
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
