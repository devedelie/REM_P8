package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.UserDao;
import com.openclassrooms.realestatemanager.models.User;

/**
 * Created by Eliran Elbaz on 11-Dec-19.
 */
public class UserDataRepository {

    private final UserDao userDao;

    public UserDataRepository(UserDao userDao) { this.userDao = userDao; }

    // --- GET USER ---
    public LiveData<User> getUser(long userId) { return this.userDao.getUser(userId); }
}
