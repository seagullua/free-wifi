package app.freewifi;

import android.app.Activity;
//import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import app.freewifi.fragments.*;

public class DetailActivity extends Activity {
  public static final String NAME = "WiFi";
  public static final String SIGNAL = "1";
  public static final String BSSID = "WiFi";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Need to check if Activity has been switched to landscape mode
    // If yes, finished and go back to the start Activity
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      finish();
      return;
    }
    setContentView(R.layout.activity_detail);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String ssid = extras.getString(NAME);
      WiFiDetail detailFragment = (WiFiDetail) getFragmentManager()
          .findFragmentById(R.id.detailFragment);
      detailFragment.setName(ssid);
      
      detailFragment.setSignalLevel(extras.getString(SIGNAL));
    }
  }
} 