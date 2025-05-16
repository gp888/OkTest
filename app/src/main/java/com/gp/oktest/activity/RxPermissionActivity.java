package com.gp.oktest.activity;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;


/**
 * Created by guoping on 2017/12/21.
 */

public class RxPermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxpermission);

//        String [] locationString = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION};
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions
//                .request(Manifest.permission.CAMERA)
//                .subscribe(granted -> {
//
//        });
    }
}
