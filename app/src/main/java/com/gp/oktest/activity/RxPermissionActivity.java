package com.gp.oktest.activity;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by guoping on 2017/12/21.
 */

public class RxPermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxpermission);

        String [] locationString = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION};
        RxPermissions rxPermissions = new RxPermissions(this);
        // Must be done during an initialization phase like onCreate
        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    // Always true pre-M
                    // I can control the camera now
                } else {
                    // Oups permission denied
                }
            }
        });

        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }
        });

        rxPermissions.requestEach(locationString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (permission.granted) {

                    }

                } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (permission.granted) {

                    }

                } else if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                    if (permission.granted) {

                    }

                }
            }
        });
    }
}
