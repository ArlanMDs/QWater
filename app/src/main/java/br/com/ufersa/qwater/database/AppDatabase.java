package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.ufersa.qwater.beans.WaterSample;
import br.com.ufersa.qwater.beans.WaterSource;

@Database(entities = {WaterSample.class, WaterSource.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WaterSampleDao waterSampleDao();
}
