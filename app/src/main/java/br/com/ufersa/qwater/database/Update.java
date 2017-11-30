package br.com.ufersa.qwater.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.com.ufersa.qwater.models.Report;

/**
 * Created by Arlan on 23-Nov-17.
 */

public class Update {

    public boolean addReport(Report report){

        SQLiteDatabase db = MainDB.getInstance().getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put("id", report.getId());
        cv.put("ca", report.getCa());
        cv.put("mg", report.getMg());
        cv.put("k", report.getK());
        cv.put("na", report.getNa());
        cv.put("co3", report.getCo3());
        cv.put("hco3", report.getHco3());
        cv.put("cl", report.getCl());
        cv.put("cea", report.getCea());
        cv.put("normalSAR", report.getNormalSAR());
        cv.put("correctedSAR", report.getCorrectedSAR());

        return  db.insert(MainDB.REPORT_TABLE, null, cv) != -1;
    }

    public boolean updateReport(Report report){

        SQLiteDatabase db = MainDB.getInstance().getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("id", report.getId());
        cv.put("ca", report.getCa());
        cv.put("mg", report.getMg());
        cv.put("k", report.getK());
        cv.put("na", report.getNa());
        cv.put("co3", report.getCo3());
        cv.put("hco3", report.getHco3());
        cv.put("cl", report.getCl());
        cv.put("cea", report.getCea());
        cv.put("normalSAR", report.getNormalSAR());
        cv.put("correctedSAR", report.getCorrectedSAR());

        String where = "id = '" + report.getId() + "'";

        return  db.update(MainDB.REPORT_TABLE, cv, where, null) > 0;
    }

}
