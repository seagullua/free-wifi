package app.freewifi;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;
    
    public static final Context getContext()
    {
    	return context;
    }

    @Override 
    public void onCreate() 
    {
        super.onCreate();
        context = getApplicationContext();
    }
    
    static {
		System.loadLibrary("FreeWifi");
	}
}
