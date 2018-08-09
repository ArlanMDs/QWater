package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.ufersa.qwater.beans.Report;
import br.com.ufersa.qwater.beans.Source;

@Database(entities = {Report.class, Source.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "appDatabase.db";
    private static volatile AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = create(context);
        }
        return appDatabase;
    }
    //The build just creates the configuration for the database so you can call this from the main thread.
    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .fallbackToDestructiveMigration()// esse método destroi a bd antigo se a versão mudar. //TODO fazer métodos de migração quando necessário
                .build();
    }

    public abstract ReportDao reportDao();
    public abstract SourceDao sourceDao();
}
