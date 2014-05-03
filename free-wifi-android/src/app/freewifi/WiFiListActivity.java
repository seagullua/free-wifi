package app.freewifi;
import java.util.ArrayList;
import java.util.HashMap;    
import java.util.List;    
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;    
import android.content.Intent;     
import android.content.IntentFilter;    
import android.net.wifi.ScanResult;    
//import android.net.wifi.WifiConfiguration;   
import android.net.wifi.WifiManager;    
import android.os.Bundle;    
//import android.util.Log;   
import android.view.View;    
import android.view.View.OnClickListener;    
//import android.widget.AdapterView;    
import android.widget.Button;    
import android.widget.ListView;    
import android.widget.SimpleAdapter;    
import android.widget.TextView;    
import android.widget.Toast;

public class WiFiListActivity extends Activity implements OnClickListener
 {      
    WifiManager wifi;       
    ListView lv;
    TextView textStatus;
    Button buttonScan;
    int size = 0;
    List<ScanResult> results;
    
    BroadcastReceiver reciever = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context c, Intent intent) 
        {
           results = wifi.getScanResults();
           size = results.size();
        }
    };
    IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifilist_activity);

        textStatus = (TextView) findViewById(R.id.textView1);
        buttonScan = (Button) findViewById(R.id.button1);
        buttonScan.setOnClickListener(this);
        lv = (ListView)findViewById(R.id.listView1);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false)
        {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }   
        this.adapter = new SimpleAdapter(WiFiListActivity.this, 
        		arraylist, android.R.layout.simple_list_item_1, 
        		new String[] { ITEM_KEY }, new int[] { android.R.id.text1 });
        lv.setAdapter(this.adapter);

        registerReceiver(reciever, filter);                    
    }

    public void onClick(View view) 
    {
        arraylist.clear();          
        wifi.startScan();

        Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
        try 
        {
            size = size - 1;
            while (size >= 0) 
            {   
                HashMap<String, String> item = new HashMap<String, String>();                       
                item.put(ITEM_KEY, results.get(size).SSID + "  " + results.get(size).capabilities);

                arraylist.add(item);
                size--;
                adapter.notifyDataSetChanged();                 
            } 
        }
        catch (Exception e)
        { }         
    }    
}