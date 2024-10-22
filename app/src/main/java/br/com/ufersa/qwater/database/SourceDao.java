package br.com.ufersa.qwater.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.ufersa.qwater.beans.Source;

@Dao
public interface SourceDao {

    @Query("SELECT * FROM Source")
    List<Source> getAll();

    @Query("SELECT name FROM Source")
    String[] getSourcesNames();

    @Insert
    void insertAll(List<Source> sources);

    @Insert
    void insert(Source source);

    @Update
    void update(Source source);

    @Delete
    void delete(Source source);

}
