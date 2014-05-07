package app.freewifi;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<ScanResult> values;

	private int selectedIndex;
	private int selectedColor = Color.parseColor("#1b1b1b");

	public MySimpleArrayAdapter(Context context, ArrayList<ScanResult> arraylist) {
		super(context, R.layout.rowlayout);
		this.context = context;
		this.values = arraylist;
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d("IN GET VIEW", "in getView");

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.name);
		TextView status = (TextView) rowView.findViewById(R.id.status);

		ImageView wifi_icon = (ImageView) rowView.findViewById(R.id.wifiicon);

		textView.setText(values.get(position).SSID);
		status.setText(values.get(position).capabilities);

		String is_free = "free";
		if (values.get(position).capabilities.contains("WEP")
				|| values.get(position).capabilities.contains("WPA")
				|| values.get(position).capabilities.contains("WPS")
				|| values.get(position).capabilities.contains("PSK")
				|| values.get(position).capabilities.contains("CCMP")
				|| values.get(position).capabilities.contains("TKIP")) {
			is_free = "keyed";
		}
		
		// add signal level
		int rssi = values.get(position).level;
		rssi = WifiManager.calculateSignalLevel(rssi, 5);
		
		if(rssi == 2)
		{
			wifi_icon.setImageResource(R.drawable.x_2_72_hdpi);
		}
		else if(rssi == 3)
		{
			wifi_icon.setImageResource(R.drawable.x_3_72_hdpi);
		}
		else if(rssi == 4)
		{
			wifi_icon.setImageResource(R.drawable.x_4_72_hdpi);
		}
		
		// Change the icon for Windows and iPhone
		// String s = values[position];
		//
		// if (s.startsWith("Windows7") || s.startsWith("iPhone")
		// || s.startsWith("Solaris")) {
		// //TODO: change imagine
		// imageView.setImageResource(R.drawable.ic_launcher);
		// } else {
		// imageView.setImageResource(R.drawable.ic_launcher);
		// }
		Log.d("free", "Item" + values.get(position).BSSID);
		return rowView;
	}
}