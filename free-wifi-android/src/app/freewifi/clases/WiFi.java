package app.freewifi.clases;


public class WiFi {
	public String SSID=""; //name
	public String BSSID; //identifier
	public int rssi;//normal
	public boolean is_open;
	
	//special for C++ part
	public boolean connect_automatically=false;
	public boolean share=true;
	public boolean has_password=false; //has pass in our BD
	
	//WAP WPS
	public String encription_system ="";
	
	public WiFi(String name, String bssid, int new_rssi, boolean is_open_net)
	{
		SSID = name;
		BSSID = bssid;
		rssi = new_rssi;
		is_open = is_open_net;
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
	
	public void setEncriptionSystem(String system){
		encription_system = system;
	}
	
	@Override
    public String toString() {
        return SSID;
    }
}
