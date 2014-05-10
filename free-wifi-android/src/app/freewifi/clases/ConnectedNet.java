package app.freewifi.clases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

public class ConnectedNet {
	private ConnectedNet() {
	}

	private static ConnectedNet instance;
	private String connected_BSSID = "";

	public static synchronized ConnectedNet getInstance() {
		if (instance == null) {
			instance = new ConnectedNet();
		}
		return instance;
	}

	public boolean hasConnectedWiFi()
	{
		return (connected_BSSID!="");
	}
	public void setConectedWiFi(String newBSSID) {
		connected_BSSID = newBSSID;
	}

	public String getConnectedWiFi() {
		return connected_BSSID;
	}

	public void findConnectedWiFi(Context context) {
		String bssid = null;
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo networkInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if (networkInfo.isConnected()) {
			final WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			
			final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
			
			if (connectionInfo != null
					&& !TextUtils.isEmpty(connectionInfo.getSSID())) {
				bssid = connectionInfo.getBSSID();
			}
		}
		connected_BSSID = bssid;
	}
}