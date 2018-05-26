package br.com.ufersa.qwater.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.ufersa.qwater.models.WaterSample;

/**
 * Created by Arlan on 23-Nov-17.
 */

public class Read {

    public ArrayList<WaterSample> getReports() {
        SQLiteDatabase db = MainDB.getInstance().getReadableDatabase();
        String query = "SELECT * FROM "+MainDB.REPORT_TABLE;
        ArrayList<WaterSample> waterSamples = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                WaterSample waterSample = new WaterSample();
                waterSample.setId(c.getInt(0));
                waterSample.setCa(c.getDouble(1));
                waterSample.setMg(c.getDouble(2));
                waterSample.setK(c.getDouble(3));
                waterSample.setNa(c.getDouble(4));
                waterSample.setCo3(c.getDouble(5));
                waterSample.setHco3(c.getDouble(6));
                waterSample.setCl(c.getDouble(7));
                waterSample.setCea(c.getDouble(8));
                waterSample.setNormalSAR(c.getDouble(9));
                waterSample.setCorrectedSAR(c.getDouble(10));
                waterSample.setCreatedAt(c.getLong(11));
                waterSamples.add(waterSample);
            }while (c.moveToNext());
        }
        c.close();
        return waterSamples;
    }


}
