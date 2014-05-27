package app.freewifi;

//in the class
//	native public static int addNumbers(int a, int b); 
//	static {
//        System.loadLibrary("FreeWifi");
//   }
//}

import java.util.ArrayList;
import java.util.List;

import app.freewifi.clases.ConnectedNet;
import app.freewifi.clases.WiFi;
import app.freewifi.fragments.*;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		WiFiList.OnItemSelectedListener {

	WifiManager wifi;
	ListView lv;
	TextView textStatus;
	Button buttonScan;
	int size = 0;
	// scan result
	List<ScanResult> results;
	MySimpleArrayAdapter adapter;

	BroadcastReceiver reciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent intent) {
			results = wifi.getScanResults();
			size = results.size();

			onClick();
		}
	};
	IntentFilter filter = new IntentFilter(
			WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

	// our in program container
	ArrayList<WiFi> arraylist = new ArrayList<WiFi>();
	
	//create Action Bar
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	  } 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		//find connected wifi
		ConnectedNet.getInstance().findConnectedWiFi(getApplicationContext());

		lv = (ListView) findViewById(R.id.list1);

		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == false) {
			Toast.makeText(getApplicationContext(),
					"wifi is disabled..making it enabled", Toast.LENGTH_LONG)
					.show();
			wifi.setWifiEnabled(true);
		}

		// set adapter
		adapter = new MySimpleArrayAdapter(MainActivity.this, arraylist);
		lv.setAdapter(adapter);

		wifi.startScan();
		// registerReceiver(reciever, filter);

		Toast.makeText(this, "Scanning....", Toast.LENGTH_SHORT).show();

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				rescan();
			}
		}, 10000);

	}

	private void rescan() {
		wifi.startScan();
		// registerReceiver(reciever, filter);

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				rescan();
			}
		}, 10000);
	}

	public void onClick() {
		arraylist.clear();
		// wifi.startScan();

		try {
			size = size - 1;
			//ConnectedNet.getInstance().findConnectedWiFi(getApplicationContext());
			int itor = 0;
			while (size >= 0) {
				ScanResult item = results.get(itor);
				itor++;

				if(!item.SSID.equals(""))
				{
					boolean is_open = isOpenWiFi(item);
					// TODO: calculate level [0-99]
					WiFi curr_net = new WiFi(item.SSID, item.BSSID, item.level,
							is_open);
					curr_net.setEncriptionSystem(item.capabilities);
					arraylist.add(curr_net);
				}
				size--;
				
				
			}

			adapter.notifyDataSetChanged();
			
			//set selected item
			
			// Log.d("free", "Notify" + String.valueOf(arraylist.size()));
		} catch (Exception e) {
		}

	}

	private boolean isOpenWiFi(ScanResult item) {
		if (item.capabilities.contains("WEP")
				|| item.capabilities.contains("WPA")
				|| item.capabilities.contains("WPS")
				|| item.capabilities.contains("PSK")
				|| item.capabilities.contains("CCMP")
				|| item.capabilities.contains("TKIP")) {
			return false;
		}
		return true;
	}

	@Override
	public void onPause() {

		super.onPause();
		unregisterReceiver(reciever);
	}

	@Override
	public void onResume() {

		super.onResume();
		registerReceiver(reciever, filter);
	}

	@Override
	public void onRssItemSelected(int position) {
		
		WiFi item = adapter.getMyItem(position);
		
		//signal level [1-5]
		int rssi = item.rssi;
		rssi = WifiManager.calculateSignalLevel(rssi, 5);
		
		//set is it free
		String is_open = "false";
		if(item.is_open)
			is_open = "true";
		
		//set has we pass in our DB
		String has_pass = "false";
		if(item.has_password)
			has_pass = "true";

		WiFiDetail fragment = (WiFiDetail) getFragmentManager()
				.findFragmentById(R.id.detailFragment);
		if (fragment != null && fragment.isInLayout()) {
			
			//refresh name
			fragment.setName(item.SSID);		
			//refresh signal info
			fragment.setSignalLevel(String.valueOf(rssi), is_open, has_pass);		
			fragment.setBSSID(String.valueOf(item.BSSID));
			
		} else {

			Intent intent = new Intent(getApplicationContext(),
					DetailActivity.class);
			
			//key of the net - BSSID
			intent.putExtra(DetailActivity.BSSID, item.BSSID);
			intent.putExtra(DetailActivity.SIGNAL, String.valueOf(rssi));
			//name of network
			intent.putExtra(DetailActivity.SSID, item.SSID);			
			intent.putExtra(DetailActivity.IS_OPEN, is_open);			
			intent.putExtra(DetailActivity.HAS_PASSWORD, has_pass);
			
			startActivity(intent);
		}

	}
	

}