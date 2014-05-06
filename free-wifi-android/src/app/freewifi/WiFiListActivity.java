package app.freewifi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class WiFiListActivity extends Activity {

	WifiManager wifi;
	ListView lv;
	TextView textStatus;
	Button buttonScan;
	int size = 0;
	List<ScanResult> results;
	MySimpleArrayAdapter adapter;

	BroadcastReceiver reciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent intent) {
			results = wifi.getScanResults();
			size = results.size();
		}
	};
	IntentFilter filter = new IntentFilter(
			WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

	// String ITEM_KEY = "key";
	ArrayList<ScanResult> arraylist = new ArrayList<ScanResult>();

	// SimpleAdapter adapter;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// String[] values = new String[] { "Android", "iPhone",
		// "WindowsMobile",
		// "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		// "Linux", "OS/2" };

		// get wifi list
		setContentView(R.layout.wifilist_activity);

		lv = (ListView) findViewById(R.id.list1);

		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == false) {
			Toast.makeText(getApplicationContext(),
					"wifi is disabled..making it enabled", Toast.LENGTH_LONG)
					.show();
			wifi.setWifiEnabled(true);
		}
		// this.adapter = new SimpleAdapter(WiFiListActivity.this,
		// arraylist,
		// android.R.layout.simple_list_item_1,
		// new String[] { ITEM_KEY },
		// new int[] { android.R.id.text1 });

		// set adapter
		adapter = new MySimpleArrayAdapter(WiFiListActivity.this, arraylist);
		lv.setAdapter(adapter);

		// lv.setAdapter(this.adapter);

		wifi.startScan();
		registerReceiver(reciever, filter);

		Toast.makeText(this, "Scanning....", Toast.LENGTH_SHORT).show();
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onClick();
			}
		}, 4000);

		// MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,
		// values);
		// setListAdapter(adapter);

		// ListView lv = getListView();

		//lv.setItemsCanFocus(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
				Toast.makeText(getApplicationContext(), "clicked item",
						Toast.LENGTH_LONG).show();
            }
//			@Override
//		      public void onItemClick(AdapterView<?> adapter, View v, int position,
//		            long arg3) 
//		      {
//		            String value = (String)adapter.getItemAtPosition(position); 
//		            Toast.makeText(getApplicationContext(), "clicked item",
//							Toast.LENGTH_LONG).show();
//		      }
			//@Override
//			public void onItemClick(AdapterView<?> adapter, View v,
//					int position, long arg3) {
//				String value = (String) adapter.getItemAtPosition(position);
//
//				adapter.setSelection(position);
//				// assuming string and if you want to get the value on click of
//				// list item
//				// do what you intend to do on click of listview row
//				Toast.makeText(getApplicationContext(), "clicked item",
//						Toast.LENGTH_LONG).show();
//
//			}
		});

	}

	public void onClick() {
		arraylist.clear();
		// wifi.startScan();

		try {
			size = size - 1;
			while (size >= 0) {
				// add signal level
				int rssi = results.get(size).level;
				rssi = WifiManager.calculateSignalLevel(rssi, 5);

				// if free wifi
				String is_free = "free";
				if (results.get(size).capabilities.contains("WEP")
						|| results.get(size).capabilities.contains("WPA")
						|| results.get(size).capabilities.contains("WPS")
						|| results.get(size).capabilities.contains("PSK")
						|| results.get(size).capabilities.contains("CCMP")
						|| results.get(size).capabilities.contains("TKIP")) {
					is_free = "keyed";
				}

				ScanResult item = results.get(size);

				// item.put(ITEM_KEY, results.get(size).SSID + " " + rssi +
				// results.get(size).capabilities + " " + is_free);
				// results.get(size).SSID + "  " +
				// results.get(size).capabilities

				arraylist.add(item);
				size--;

			}
			adapter.notifyDataSetChanged();
			Log.d("free", "Notify" + String.valueOf(arraylist.size()));
		} catch (Exception e) {
		}

	}

}

// public class WiFiListActivity extends Activity
// {
// WifiManager wifi;
// ListView lv;
// TextView textStatus;
// Button buttonScan;
// int size = 0;
// List<ScanResult> results;
//
// BroadcastReceiver reciever = new BroadcastReceiver()
// {
// @Override
// public void onReceive(Context c, Intent intent)
// {
// results = wifi.getScanResults();
// size = results.size();
// }
// };
// IntentFilter filter = new
// IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//
// String ITEM_KEY = "key";
// ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String,
// String>>();
// SimpleAdapter adapter;
//
// @Override
// public void onCreate(Bundle savedInstanceState)
// {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.wifilist_activity);
//
// lv = (ListView)findViewById(R.id.listView1);
//
// wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
// if (wifi.isWifiEnabled() == false)
// {
// Toast.makeText(getApplicationContext(),
// "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
// wifi.setWifiEnabled(true);
// }
// this.adapter = new SimpleAdapter(WiFiListActivity.this,
// arraylist,
// android.R.layout.simple_list_item_1,
// new String[] { ITEM_KEY }, new int[] { android.R.id.text1 });
//
// lv.setAdapter(this.adapter);
//
// wifi.startScan();
// registerReceiver(reciever, filter);
//
//
// Toast.makeText(this, "Scanning....", Toast.LENGTH_SHORT).show();
// final Handler handler = new Handler();
// handler.postDelayed(new Runnable() {
// @Override
// public void run() {
// onClick();
// }
// }, 4000);
//
// }
//
// public void onClick()
// {
// arraylist.clear();
// //wifi.startScan();
//
// try
// {
// size = size - 1;
// while (size >= 0)
// {
// //add signal level
// int rssi = results.get(size).level;
// rssi = WifiManager.calculateSignalLevel(rssi, 5);
//
//
// //if free wifi
// String is_free = "free";
// if(results.get(size).capabilities.contains("WEP") ||
// results.get(size).capabilities.contains("WPA") ||
// results.get(size).capabilities.contains("WPS") ||
// results.get(size).capabilities.contains("PSK") ||
// results.get(size).capabilities.contains("CCMP") ||
// results.get(size).capabilities.contains("TKIP"))
// {
// is_free = "keyed";
// }
//
// HashMap<String, String> item = new HashMap<String, String>();
// item.put(ITEM_KEY, results.get(size).SSID + " " + rssi +
// results.get(size).capabilities + " " + is_free);
// //results.get(size).SSID + "  " + results.get(size).capabilities
//
// arraylist.add(item);
// size--;
// adapter.notifyDataSetChanged();
// }
// }
// catch (Exception e)
// { }
// }
// }