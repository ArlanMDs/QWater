package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.ufersa.qwater.beans.Report;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM Report")
    List<Report> getAll();

   // @Query("SELECT * FROM watersample WHERE name LIKE :name LIMIT 1")
  //  Product findByName(String name);

    @Insert
    void insertAll(List<Report> reports);

    @Insert
    void insert(Report report);

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("SELECT * FROM Report WHERE souId IS :wat_souID")
    List<Report> getWaterSampleForSite(int wat_souID);
}
