package com.example.jack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Jacktivity extends Activity 
{
    private static final String TAG = Jacktivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        // request to turn the title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // make it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the view
        setContentView(new MainGamePanel(this));
        Log.d (TAG, "View added.");
    }

    protected void onDestroy()
    {
    	Log.d(TAG, "Destroying...");
    	super.onDestroy();
    }

    protected void onStop()
    {
    	Log.d(TAG, "Stopping...");
    	super.onStop();
    }
}
