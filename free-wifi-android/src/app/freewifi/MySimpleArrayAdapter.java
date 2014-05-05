package app.freewifi;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
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

  public MySimpleArrayAdapter(Context context, 
		  ArrayList<ScanResult> arraylist) {
    super(context, R.layout.rowlayout);
    this.context = context;
    this.values = arraylist;
  }
  public void update(ArrayList<ScanResult> new_values) {
	  values.clear();
	  values.addAll(new_values);
	  this.notifyDataSetChanged();
	}
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    
	  LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
	  View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    
	  TextView textView = (TextView) rowView.findViewById(R.id.name);
    
	  ImageView imageView = (ImageView) rowView.findViewById(R.id.wifiicon);
    
	  textView.setText(values.get(position).BSSID);
    
	  // Change the icon for Windows and iPhone
//	  String s = values[position];
//	  
//		if (s.startsWith("Windows7") || s.startsWith("iPhone")
//		    || s.startsWith("Solaris")) {
//			//TODO: change imagine
//		  imageView.setImageResource(R.drawable.ic_launcher);
//		} else {
//		  imageView.setImageResource(R.drawable.ic_launcher);
//		}
		
		

    return rowView;
  }
} 