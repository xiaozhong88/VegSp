package com.atinytot.vegsp_v_1.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.atinytot.vegsp_v_1.room.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WORD ORDER BY id DESC")
    LiveData<List<User>> getAllUsersLive();

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE un LIKE :username AND +" + "pw LIKE :password LIMIT 1")
    User findUser(String username, String password);

    @Insert
    void insertUsers(User... users);

    @Delete
    void deleteUsers(User... user);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Update
    void updateUsers(User... users);
}
