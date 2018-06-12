package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.ufersa.qwater.beans.WaterReport;

@Dao
public interface WaterReportDao {

    @Query("SELECT * FROM WaterReport")
    List<WaterReport> getAll();

   // @Query("SELECT * FROM watersample WHERE name LIKE :name LIMIT 1")
  //  Product findByName(String name);

    @Insert
    void insertAll(List<WaterReport> waterReports);

    @Insert
    void insert(WaterReport waterReport);

    @Update
    void update(WaterReport waterReport);

    @Delete
    void delete(WaterReport waterReport);

    @Query("SELECT * FROM WaterReport WHERE wat_souID IS :wat_souID")
    List<WaterReport> getWaterSampleForSite(int wat_souID);
}
