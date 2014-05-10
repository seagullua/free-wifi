package app.freewifi;

//in the class
//	native public static int addNumbers(int a, int b); 
//	static {
//        System.loadLibrary("FreeWifi");
//   }
//}

import java.util.ArrayList;
import java.util.List;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		// Log.d("TAG", String.valueOf(addNumbers(3, 5)));

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
			while (size >= 0) {
				ScanResult item = results.get(size);

				boolean is_keyed = isKeyedWiFi(item);
				// TODO: calculate
				WiFi curr_net = new WiFi(item.SSID, item.BSSID, item.level,
						is_keyed);
				arraylist.add(curr_net);
				size--;
			}

			adapter.notifyDataSetChanged();

			// Log.d("free", "Notify" + String.valueOf(arraylist.size()));
		} catch (Exception e) {
		}

	}

	private boolean isKeyedWiFi(ScanResult item) {
		if (item.capabilities.contains("WEP")
				|| item.capabilities.contains("WPA")
				|| item.capabilities.contains("WPS")
				|| item.capabilities.contains("PSK")
				|| item.capabilities.contains("CCMP")
				|| item.capabilities.contains("TKIP")) {
			return true;
		}
		return false;
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

		WiFiDetail fragment = (WiFiDetail) getFragmentManager()
				.findFragmentById(R.id.detailFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.setName(item.SSID);
		} else {

			Intent intent = new Intent(getApplicationContext(),
					DetailActivity.class);
			intent.putExtra(DetailActivity.NAME, item.BSSID);

			int rssi = item.rssi;
			rssi = WifiManager.calculateSignalLevel(rssi, 5);
			intent.putExtra(DetailActivity.SIGNAL, String.valueOf(rssi));

			intent.putExtra(DetailActivity.BSSID, item.SSID);
			startActivity(intent);
		}

	}

	native public static int addNumbers(int a, int b);

	static {
		System.loadLibrary("FreeWifi");
	}

}