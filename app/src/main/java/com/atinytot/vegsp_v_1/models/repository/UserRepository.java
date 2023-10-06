package com.atinytot.vegsp_v_1.models.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.atinytot.vegsp_v_1.room.UserDataBase;
import com.atinytot.vegsp_v_1.room.dao.UserDao;
import com.atinytot.vegsp_v_1.room.entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class UserRepository {

    private List<User> allUsers;
    private UserDao userDao;
    private boolean exit;

    public UserRepository(Context context) {
        UserDataBase userDataBase = UserDataBase.getDataBase(context);
        this.userDao = userDataBase.getUserDao();
    }

    public void insertUsers(User... users) {
        new InsertAsyncTask(userDao).execute(users);
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public InsertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(@NonNull User... users) {
            for (User user : users) {
                List<User> u = userDao.getAll();
                if (u.size() == 0) {
                    userDao.insertUsers(users);
                } else {
                    for (User us : u) {
                        if (!us.getUsername().equals(user.username)) {
                            userDao.insertUsers(users);
                        }
                    }
                }
            }
            return null;
        }
    }

    public void deleteUsers(User... users) {
        new DeleteAsyncTask(userDao).execute(users);
    }

    private static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public DeleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(@NotNull User... users) {
            userDao.deleteUsers(users);
            return null;
        }
    }

    public void deleteAllUsers(User... users) {
        new DeleteAllUsersAsyncTask(userDao).execute();
    }

    private static class DeleteAllUsersAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;

        public DeleteAllUsersAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllUsers();
            return null;
        }
    }

    public void updateUsers(User... users) {
        new UpdateAsyncTask(userDao).execute(users);
    }

    private static class UpdateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public UpdateAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUsers(users);
            return null;
        }
    }

//    public void getAllUsers() {
//        new GetAllUsersAsyncTask(userDao, new GetAllUsersAsyncTask.OnGetAllUsersListener() {
//
//            @Override
//            public void onResult(List<User> users) {
//                allUsersLive.setValue(users);
////                Log.i("post:", allUsers.toString());
//            }
//        }).execute();
////        Log.i("post:", String.valueOf(allUsers.isEmpty()));
//    }

    public LiveData<List<User>> getAllUsersLive() {

        return userDao.getAllUsersLive();
    }

    public void findUser(String username, String password, OnResultCallback<Result<User>> callback) {
        new FindUserAsyncTask(userDao, callback).execute(username, password);
    }

    private static class FindUserAsyncTask extends AsyncTask<String, Void, Result<User>> {

        private UserDao userDao;
        private OnResultCallback<Result<User>> callback;

        public FindUserAsyncTask(UserDao userDao, OnResultCallback<Result<User>> callback) {
            this.userDao = userDao;
            this.callback = callback;
        }

        @Override
        protected Result<User> doInBackground(@NotNull String... strings) {
            String username = strings[0];
            String password = strings[1];

            User user = userDao.findUser(username, password);
            if (user == null) {
                return new Result.Failure(new Exception("用户名或密码错误"));
            } else {
                return new Result.Success(user);
            }
        }

        @Override
        protected void onPostExecute(Result<User> result) {
            callback.onResult(result);
        }

    }

}
