package br.com.ufersa.qwater.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.ufersa.qwater.beans.Source;

@Dao
public interface SourceDao {

    @Query("SELECT * FROM Source")
    List<Source> getAll();

    @Insert
    void insertAll(List<Source> sources);

    @Insert
    void insert(Source source);

    @Update
    void update(Source source);

    @Delete
    void delete(Source source);

}
