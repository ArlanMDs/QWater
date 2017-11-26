package br.com.ufersa.qwater;

import android.app.Application;
import android.content.Context;

/**
 * Created by Arlan on 23-Nov-17.
 */

public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        super.onCreate();
    }

    public  static Context getContext(){
        return mContext;
    }
}
