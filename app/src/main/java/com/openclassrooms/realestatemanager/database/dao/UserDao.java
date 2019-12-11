package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.User;

/**
 * Created by Eliran Elbaz on 11-Dec-19.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUser(User user);

    @Query("SELECT * FROM User WHERE id = :userId")
    LiveData<User> getUser(long userId);
}
