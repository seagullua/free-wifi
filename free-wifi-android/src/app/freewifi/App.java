package app.freewifi;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context = null;
    
    public static final Context getContext()
    {
    	return context;
    }

    public static String getWritablePath()
    {
    	Context context = getContext();
    	if(context != null)
    		return context.getFilesDir().getAbsolutePath();
    	else
    		return "";
    }
    
    @Override 
    public void onCreate() 
    {
        super.onCreate();
        context = getApplicationContext();
        initLibrary();
    }
    
    static native void initLibrary();
    
    static {
		System.loadLibrary("FreeWifi");
	}
}
