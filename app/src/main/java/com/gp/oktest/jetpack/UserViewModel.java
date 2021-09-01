package com.gp.oktest.jetpack;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class UserViewModel extends ViewModel {

    private MutableLiveData<String> userLiveData ;
    private MutableLiveData<Boolean> loadingLiveData;

    public UserViewModel() {
        userLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    //获取用户信息,假装网络请求 2s后 返回用户信息
    public void getUserInfo() {

        loadingLiveData.setValue(true);
        UserRepository.getUserRepository().getUsersFromServer(new UserRepository.Callback<String>() {
            @Override
            public void onSuccess(String users) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(users);
            }

            @Override
            public void onFailed(String msg) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(null);
            }
        });
    }

    public LiveData<String> getUserLiveData() {
        return userLiveData;
    }
    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }
}
