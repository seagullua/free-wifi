package app.freewifi;


public class WiFi {
	String SSID; //name
	String BSSID; //identifier
	int converted_rssi;//0-100
	boolean keyed;
	
	//special for C++ part
	boolean connect_automatically;
	boolean share;
	boolean has_password; //has pass in our BD
	
	public WiFi(String name, String bssid, int rssi, boolean keyed_value)
	{
		SSID = name;
		BSSID = bssid;
		converted_rssi = rssi;
		keyed = keyed_value;
	}
	
	public void setAutoConnect(boolean connect)
	{
		connect_automatically = connect;
	}
	public void setShare(boolean is_share)
	{
		share = is_share;
	}
	public void setHasPassword(boolean has_pass)
	{
		has_password = has_pass;
	}
}
