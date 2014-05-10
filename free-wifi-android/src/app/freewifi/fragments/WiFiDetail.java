package app.freewifi.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.freewifi.R;

public class WiFiDetail extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail,
        container, false);
    
    return view;
  }

  public void setName(String item) {
    TextView view = (TextView) getView().findViewById(R.id.name);
    view.setText(item);
  }
  
  public void setSignalLevel(String level, String is_open, String has_password)
  {
	  ImageView view = (ImageView) getView().findViewById(R.id.signal);  
  }
  
  public void setBSSID(String bssid)
  {
	  //is this wifi connected or no
  }
} 