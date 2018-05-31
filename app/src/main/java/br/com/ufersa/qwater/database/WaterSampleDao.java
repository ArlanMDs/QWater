package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.ufersa.qwater.beans.WaterSample;

@Dao
public interface WaterSampleDao {

    @Query("SELECT * FROM watersample")
    List<WaterSample> getAll();

   // @Query("SELECT * FROM watersample WHERE name LIKE :name LIMIT 1")
  //  Product findByName(String name);

    @Insert
    void insertAll(List<WaterSample> waterSamples);

    @Insert
    void insert(WaterSample waterSample);

    @Update
    void update(WaterSample waterSample);

    @Delete
    void delete(WaterSample waterSample);

    @Query("SELECT * FROM watersample WHERE wat_souName IS :wat_souName")
    List<WaterSample> getWaterSampleForSite(int wat_souName);
}
