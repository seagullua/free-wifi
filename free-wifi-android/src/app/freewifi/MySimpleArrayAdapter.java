package app.freewifi;

import java.util.ArrayList;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.freewifi.clases.WiFi;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<WiFi> values;


	public MySimpleArrayAdapter(Context context, ArrayList<WiFi> arraylist) {
		super(context, R.layout.rowlayout);
		this.context = context;
		this.values = arraylist;
	}

	@Override
	public int getCount() {
		return values.size();
	}

	public WiFi getMyItem(int arg0) {
		if (null != values) {
			try {
				return values.get(arg0);
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.name);
		TextView status = (TextView) rowView.findViewById(R.id.status);

		ImageView wifi_icon = (ImageView) rowView.findViewById(R.id.wifiicon);

		textView.setText(values.get(position).SSID);
		// status.setText(values.get(position).capabilities);
		status.setText("");

		boolean is_open = values.get(position).is_open;

		// add signal level
		int rssi = values.get(position).rssi;
		rssi = WifiManager.calculateSignalLevel(rssi, 5);

		if (!is_open) {
			if (rssi == 1)
				wifi_icon.setImageResource(R.drawable.x_1_72_lock_hdpi);
			else if (rssi == 2)
				wifi_icon.setImageResource(R.drawable.x_2_72_lock_hdpi);
			else if (rssi == 3)
				wifi_icon.setImageResource(R.drawable.x_3_72_lock_hdpi);
			else if (rssi == 4)
				wifi_icon.setImageResource(R.drawable.x_4_72_lock_hdpi);
		} else {
			if (rssi == 2)
				wifi_icon.setImageResource(R.drawable.x_2_72_hdpi);
			else if (rssi == 3)
				wifi_icon.setImageResource(R.drawable.x_3_72_hdpi);
			else if (rssi == 4)
				wifi_icon.setImageResource(R.drawable.x_4_72_hdpi);
		}

		// Log.d("free", "Item" + values.get(position).BSSID);
		return rowView;
	}

	public void changeRowColor(int position) {
		
		
	}
}