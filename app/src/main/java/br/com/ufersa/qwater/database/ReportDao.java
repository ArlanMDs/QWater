package br.com.ufersa.qwater.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
