package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.ufersa.qwater.beans.WaterSource;

@Dao
public interface WaterSourceDao {

    @Query("SELECT * FROM watersource")
    List<WaterSource> getAll();

    @Insert
    void insertAll(List<WaterSource> waterSources);

    @Insert
    void insert(WaterSource watersource);

    @Update
    void update(WaterSource watersource);

    @Delete
    void delete(WaterSource watersource);

}
