package br.com.ufersa.qwater.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Arlan on 23-Nov-17.
 */

public class Create {

    public void createTable(){
        SQLiteDatabase db = MainDB.getInstance().getWritableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS " + MainDB.REPORT_TABLE + "(\n" +
                "[id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "[ca] REAL,\n" +
                "[mg] REAL,\n" +
                "[k] REAL,\n" +
                "[na] REAL,\n" +
                "[co3] REAL,\n" +
                "[hco3] REAL,\n" +
                "[cl] REAL,\n" +
                "[cea] REAL,\n" +
                "[normalSAR] REAL,\n" +
                "[correctedSAR] REAL\n" +
                ")";

        db.execSQL(query);
    }

}
