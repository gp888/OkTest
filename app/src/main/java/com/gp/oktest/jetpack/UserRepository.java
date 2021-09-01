package com.gp.oktest.jetpack;

import android.os.AsyncTask;

import com.gp.oktest.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserRepository {

    private static UserRepository mUserRepository;

    public static UserRepository getUserRepository() {
        if (mUserRepository == null) {
            mUserRepository = new UserRepository();
        }
        return mUserRepository;
    }

    //(假装)从服务端获取
    public void getUsersFromServer(Callback<String> callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String users) {
                callback.onSuccess(users);
                //存本地数据库
                saveUsersToLocal(users);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //假装从服务端获取的
                List<User> users = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    User user = new User("user" + i, i);
                    users.add(user);
                }
                return "";
            }
        }.execute();
    }

    //从本地数据库获取
    public void getUsersFromLocal(){

    }

    //存入本地数据库 (从服务端获取数据后可以调用)
    private void saveUsersToLocal(String users){

    }

    interface Callback<T>{
        void onSuccess(T s);
        void onFailed(T s);
    }
}
