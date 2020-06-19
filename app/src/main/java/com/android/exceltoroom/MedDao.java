package com.android.exceltoroom;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedDao {
    @Query("SELECT * FROM med")
    List<Med> getAll();
    
    @Query("SELECT * FROM med WHERE id = :id")
    List<Med> loadAllById(int id);
    
    @Insert
    void insertAll(Med... meds);
    
    @Delete
    void delete(Med med);
    
    @Update
    void update(Med med);
    
    @Query("DELETE FROM Med")
    void deleteAll();
    
    @Query("SELECT COUNT(id) FROM Med")
    int getCount();
}