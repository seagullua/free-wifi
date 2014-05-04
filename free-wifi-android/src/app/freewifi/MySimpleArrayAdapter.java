package app.freewifi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;
  
  private int selectedIndex;
  private int selectedColor = Color.parseColor("#1b1b1b");

  public MySimpleArrayAdapter(Context context, String[] values) {
    super(context, R.layout.rowlayout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    
	  LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
	  View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    
	  TextView textView = (TextView) rowView.findViewById(R.id.name);
    
	  ImageView imageView = (ImageView) rowView.findViewById(R.id.wifiicon);
    
	  textView.setText(values[position]);
    
	  // Change the icon for Windows and iPhone
	  String s = values[position];
	  
		if (s.startsWith("Windows7") || s.startsWith("iPhone")
		    || s.startsWith("Solaris")) {
			//TODO: change imagine
		  imageView.setImageResource(R.drawable.ic_launcher);
		} else {
		  imageView.setImageResource(R.drawable.ic_launcher);
		}
		
		

    return rowView;
  }
} 