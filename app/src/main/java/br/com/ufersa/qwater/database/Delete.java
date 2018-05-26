package br.com.ufersa.qwater.database;

import android.database.sqlite.SQLiteDatabase;

import br.com.ufersa.qwater.models.WaterSample;

/**
 * Created by Arlan on 24-Nov-17.
 */

public class Delete {

    public void removeTable(){
        SQLiteDatabase db = MainDB.getInstance().getWritableDatabase();

        String query="DROP TABLE IF EXISTS "+ MainDB.REPORT_TABLE;

        db.execSQL(query);
    }

    public boolean removeReport(WaterSample waterSample){
        SQLiteDatabase db = MainDB.getInstance().getWritableDatabase();

        String query = "id = '"+ waterSample.getId() + "'";

        return db.delete(MainDB.REPORT_TABLE, query, null) > 0;
    }

}
