package app.freewifi;

import java.util.ArrayList;

import android.content.Context;
//import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.freewifi.clases.ConnectedNet;
import app.freewifi.clases.WiFi;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<WiFi> values;
	
	private View rowView;

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

		rowView = inflater.inflate(R.layout.rowlayout, parent, false);

		TextView name = (TextView) rowView.findViewById(R.id.name);
		TextView status = (TextView) rowView.findViewById(R.id.status);

		ImageView wifi_icon = (ImageView) rowView.findViewById(R.id.wifiicon);

		WiFi w = values.get(position);
		//Log.w("ii", String.valueOf(position)+" "+String.valueOf(values.size()));
		name.setText(w.SSID);
		status.setText("");

		boolean is_open = values.get(position).is_open;

		// add signal level
		int rssi = values.get(position).rssi;
		rssi = WifiManager.calculateSignalLevel(rssi, 5);

		if (!values.get(position).has_password) {
			if (!is_open) {
				if (rssi == 1)
					wifi_icon.setImageResource(R.drawable.blue_1_lock);
				else if (rssi == 2)
					wifi_icon.setImageResource(R.drawable.blue_2_lock);
				else if (rssi == 3)
					wifi_icon.setImageResource(R.drawable.blue_3_lock);
				else if (rssi == 4)
					wifi_icon.setImageResource(R.drawable.blue_4_lock);
			} else {
				if(rssi == 1)
					wifi_icon.setImageResource(R.drawable.blue_1);
				else if (rssi == 2)
					wifi_icon.setImageResource(R.drawable.blue_2);
				else if (rssi == 3)
					wifi_icon.setImageResource(R.drawable.blue_3);
				else if (rssi == 4)
					wifi_icon.setImageResource(R.drawable.blue_4);
			}
		} else {
			// there is only with key
			if (rssi == 1)
				wifi_icon.setImageResource(R.drawable.orange_1);
			else if (rssi == 2)
				wifi_icon.setImageResource(R.drawable.orange_2);
			else if (rssi == 3)
				wifi_icon.setImageResource(R.drawable.orange_3);
			else if (rssi == 4)
				wifi_icon.setImageResource(R.drawable.orange_4);
		}

		// if it is connected net
		if (ConnectedNet.getInstance().hasConnectedWiFi()
				&& ConnectedNet.getInstance().getConnectedWiFi()
						.equals(values.get(position).BSSID)) {
			status.setText("Connected");
			name.setTextColor(context.getResources().getColor(R.color.blue));
			
		}
		else{
			//WAP-WPS
			status.setText(w.encription_system);
			
			if(w.is_open)
				status.setText(context.getResources().getString(R.string.status_open));
			else if (w.has_password)
				status.setText(context.getResources().getString(R.string.status_our));
			else
				status.setText(context.getResources().getString(R.string.status_close));
			
		}
		//set status to other wifi
		
		return rowView;
	}

	public void changeRowColor(int position) {

	}
	
	public String getWiFiName()
	{
		TextView name = (TextView) rowView.findViewById(R.id.name);
		return name.toString();
	}
}