package com.example.lab2phonedatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {

    @Query("SELECT * FROM phones")
    LiveData<List<Phone>> getAllPhones();

    @Insert
    void insertPhone(Phone phone);

    @Delete
    void deletePhone(Phone phone);

    @Query("DELETE FROM phones")
    void deleteAllPhones();

    @Update
    void updatePhone(Phone phone);

}
