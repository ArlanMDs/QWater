package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.ufersa.qwater.beans.WaterSample;
import br.com.ufersa.qwater.beans.WaterSource;

@Database(entities = {WaterSample.class, WaterSource.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "appDatabase.db";
    private static volatile AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = create(context);
        }
        return appDatabase;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).build();
    }

    public abstract WaterSampleDao waterSampleDao();
    public abstract WaterSourceDao waterSourceDao();
}
