package app.freewifi.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import app.freewifi.R;

public class WiFiList extends Fragment {


  private OnItemSelectedListener listener;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
//    View view = inflater.inflate(R.layout.fragment_list,
//        container, false);
	  View view = inflater.inflate(R.layout.wifilist_activity,
		        container, false);
//    Button button = (Button) view.findViewById(R.id.button1);
//    button.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        updateDetail();
//      }
//    });
	  
	  ListView lv = (ListView) view.findViewById(R.id.list1);
	  lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, 
				int position,
				long id) {
			// TODO: Change the code
			updateDetail(position);
		}
	});
	  
    return view;
  }

  public interface OnItemSelectedListener {
    public void onRssItemSelected(int position);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnItemSelectedListener) {
      listener = (OnItemSelectedListener) activity;
    } else {
      throw new ClassCastException(activity.toString()
          + " must implemenet MyListFragment.OnItemSelectedListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    listener = null;
  }

  // may also be triggered from the Activity
  public void updateDetail(int position) {
    listener.onRssItemSelected(position);
  }
} 

