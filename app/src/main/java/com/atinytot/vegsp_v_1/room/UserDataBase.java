package com.atinytot.vegsp_v_1.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.atinytot.vegsp_v_1.room.dao.UserDao;
import com.atinytot.vegsp_v_1.room.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    private final static String DATABASE_NAME = "user_database.db";
    private static UserDataBase INSTANCE;

    public static synchronized UserDataBase getDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, DATABASE_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao getUserDao();
}
