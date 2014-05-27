package app.freewifi.clases;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
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

	public String findConnectedWiFi(Context context) {
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
		return connected_BSSID;
	}
	
	public void disconectWiFi(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
		wifiManager.setWifiEnabled(false);
	}
	
	public void connectWiFi(Context context, String ssid, String pass,
			String sequred_type){
		
		WifiConfiguration conf = new WifiConfiguration();
		conf.SSID = "\"" + ssid + "\"";   
		
		// Please note the quotes. String should contain ssid in quotes
		//Then, for WEP network you need to do this:
		if(sequred_type.contains("WEP")){
			conf.wepKeys[0] = "\"" + pass + "\""; 
			conf.wepTxKeyIndex = 0;
			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40); 
		}
		
		
		//For WPA network you need to add passphrase like this:
		if(sequred_type.contains("WPA")){
			conf.preSharedKey = "\""+ pass +"\"";
		}
		
		
		//For Open network you need to do this:
		if(pass.equals(""))
			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		
		
		
		//Then, you need to add it to Android wifi manager settings:
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
		wifiManager.addNetwork(conf);
		
		//And finally, you might need to enable it, so Android conntects to it:
		List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
		for( WifiConfiguration i : list ) {
		    if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
		         wifiManager.disconnect();
		         wifiManager.enableNetwork(i.networkId, true);
		         wifiManager.reconnect();               

		         break;
		    }           
		 }
		
		//set new connected wifi
		findConnectedWiFi(context);
	}
}