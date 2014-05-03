package app.freewifi;
//in the class
//	native public static int addNumbers(int a, int b); 
//	static {
//        System.loadLibrary("FreeWifi");
//   }
//}

import app.freewifi.fragments.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.Menu;

public class MainActivity extends Activity implements
WiFiList.OnItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public void onRssItemSelected(String link) {
//	  WiFiDetail fragment = (WiFiDetail) getFragmentManager()
//        .findFragmentById(R.id.detailFragment);
//    if (fragment != null && fragment.isInLayout()) {
//      fragment.setText(link);
//    } else {
//      Intent intent = new Intent(getApplicationContext(),
//          DetailActivity.class);
//      intent.putExtra(DetailActivity.EXTRA_URL, link);
//      startActivity(intent);
//
//    }
	  
	  Intent intent = new Intent(this, WiFiListActivity.class);
	    startActivity(intent);
  }

}  