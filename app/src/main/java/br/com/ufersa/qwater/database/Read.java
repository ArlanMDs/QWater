package br.com.ufersa.qwater.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.ufersa.qwater.models.Report;

/**
 * Created by Arlan on 23-Nov-17.
 */

public class Read {

    public ArrayList<Report> getReports() {
        SQLiteDatabase db = MainDB.getInstance().getReadableDatabase();
        String query = "SELECT * FROM "+MainDB.REPORT_TABLE;
        ArrayList<Report> reports = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Report report = new Report();
                report.setId(c.getInt(0));
                report.setCa(c.getDouble(1));
                report.setMg(c.getDouble(2));
                report.setK(c.getDouble(3));
                report.setNa(c.getDouble(4));
                report.setCo3(c.getDouble(5));
                report.setHco3(c.getDouble(6));
                report.setCl(c.getDouble(7));
                report.setCea(c.getDouble(8));
                report.setNormalSAR(c.getDouble(9));
                report.setCorrectedSAR(c.getDouble(10));
                report.setCreatedAt(c.getLong(11));
                reports.add(report);
            }while (c.moveToNext());
        }
        c.close();
        return reports;
    }


}
