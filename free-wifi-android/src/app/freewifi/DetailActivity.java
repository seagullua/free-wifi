package app.freewifi;

import android.app.Activity;
//import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.freewifi.clases.ConnectedNet;
import app.freewifi.fragments.*;

public class DetailActivity extends Activity {
	public static final String BSSID = "key";
	public static final String SIGNAL = "1";
	public static final String SSID = "WIFiName";
	public static final String IS_OPEN = "true"; // it is open
	public static final String HAS_PASSWORD = "false"; // in BD we has pass

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Need to check if Activity has been switched to landscape mode
		// If yes, finished and go back to the start Activity
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		setContentView(R.layout.activity_detail);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			//set wifi name
			String ssid = extras.getString(SSID);
			WiFiDetail detailFragment = (WiFiDetail) getFragmentManager()
					.findFragmentById(R.id.detailFragment);
			detailFragment.setName(ssid);

			//set signal level - signal[1-5] + is_open + has_key
			detailFragment.setSignalLevel(extras.getString(SIGNAL),
					extras.getString(IS_OPEN),
					extras.getString(HAS_PASSWORD));
			
			//get bssid(key) to know is we connected to this 
			detailFragment.setBSSID(extras.getString(BSSID));
		}
		
		//add on button Connect  function
		//addListenerOnButton();
		
	}
	
	
	public void addListenerOnButton() {
		 
		Button connect_button = (Button) findViewById(R.id.button_connect);
		Button disconnect_button = (Button) findViewById(R.id.forget);
 
		connect_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//add toast
				Toast.makeText(getApplicationContext(),
						"conecting...", 
						   Toast.LENGTH_LONG).show();
				//call connect function
				EditText pass = (EditText) findViewById(R.id.pass);
				String pass_text = "";
				if(!pass.isEnabled())
					pass_text = pass.getText().toString();
				
				//TODO: set WEP or WPA
				String connection_type="";
				
				TextView name = (TextView) findViewById(R.id.wifi_name);
				String wifi_name = name.getText().toString();
				
				ConnectedNet.getInstance().connectWiFi(getApplicationContext(),
						wifi_name, pass_text, connection_type);
			}
 
		});
		
		disconnect_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//add toast
				Toast.makeText(getApplicationContext(),
						"disconecting...", 
						   Toast.LENGTH_LONG).show();
				//call disconnect function
				ConnectedNet.getInstance().disconectWiFi(getApplicationContext());
			}
 
		});
 
	}
 
}