package br.com.ufersa.qwater.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.ufersa.qwater.MyApp;

/**
 * Created by Arlan on 23-Nov-17.
 *
 * documentação: https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#getReadableDatabase%28%29
 */

public class MainDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "QWater";
    private static int DB_VERSION = 1;
    public static final String REPORT_TABLE = "report";

    private static MainDB instance;

    public static MainDB getInstance(){
        if(instance==null) instance = new MainDB();
        return instance;
    }

    private MainDB() {
        super(MyApp.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        instance = null;
        super.close();
    }
}
