package app.freewifi.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import app.freewifi.R;
import app.freewifi.clases.ConnectedNet;

public class WiFiDetail extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_detail, container, false);

		return view;
	}

	public void setName(String item) {
		TextView view = (TextView) getView().findViewById(R.id.name);
		view.setText(item);
	}

	public void setSignalLevel(String level, String is_open, String has_password) {
		// TODO: set signal level image

		EditText pass_field = (EditText) getView().findViewById(R.id.pass);
		CheckBox share_box = (CheckBox) getView().findViewById(R.id.share);
		Button connect_button = (Button) getView().findViewById(R.id.button_connect);
		Button forgett_button = (Button) getView().findViewById(R.id.forget);

		if (is_open == "true" || has_password == "true" ||
				(is_open == "false" && has_password == "true")) {
			
			//HIDE
			// hide password EditText field
			pass_field.setVisibility(View.INVISIBLE);

			// hide forget button
			forgett_button.setVisibility(View.GONE);

			// hide share password check box
			share_box.setVisibility(View.GONE);
			
			//SHOW
			connect_button.setVisibility(View.VISIBLE);
			
		} else if (is_open == "false" && has_password == "false") {
			
			//HIDE
			// hide forget button
			forgett_button.setVisibility(View.GONE);
			
			//SHOW
			pass_field.setVisibility(View.VISIBLE);
			connect_button.setVisibility(View.VISIBLE);
			share_box.setVisibility(View.VISIBLE);
		}

	}

	public void setBSSID(String bssid) {
		// is this wifi connected or no
		if (ConnectedNet.getInstance().hasConnectedWiFi()
				&& ConnectedNet.getInstance().getConnectedWiFi() == bssid) {
			
			EditText pass_field = (EditText) getView().findViewById(R.id.pass);
			CheckBox share_box = (CheckBox) getView().findViewById(R.id.share);
			Button connect_button = (Button) getView().findViewById(R.id.button_connect);
			Button forgett_button = (Button) getView().findViewById(R.id.forget);
			
			//HIDE
			// hide password EditText field
			pass_field.setVisibility(View.INVISIBLE);

			// hide share field
			share_box.setVisibility(View.GONE);
			
			//hide connect button
			connect_button.setVisibility(View.GONE);
			
			//SHOW
			forgett_button.setVisibility(View.VISIBLE);
		}
	}
}