package app.freewifi;

import java.util.ArrayList;
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

	ArrayList<ScanResult> arraylist = new ArrayList<ScanResult>();

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

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

		// set adapter
		adapter = new MySimpleArrayAdapter(WiFiListActivity.this, arraylist);
		lv.setAdapter(adapter);

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


		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
				Toast.makeText(getApplicationContext(), "clicked item" + position,
						Toast.LENGTH_LONG).show();
            }
		});

	}

	public void onClick() {
		arraylist.clear();
		// wifi.startScan();

		try {
			size = size - 1;
			while (size >= 0) {
				ScanResult item = results.get(size);
				arraylist.add(item);
				size--;
			}
			
			adapter.notifyDataSetChanged();
			
			Log.d("free", "Notify" + String.valueOf(arraylist.size()));
		} catch (Exception e) {
		}

	}

}