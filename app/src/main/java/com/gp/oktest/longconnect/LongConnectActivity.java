package com.gp.oktest.longconnect;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gp.oktest.base.BaseActivity;

import static android.content.Context.BIND_AUTO_CREATE;

public class LongConnectActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, TcpService.class);
        bindService(intent, connection,BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TcpService.ClientBinder clientBinder = (TcpService.ClientBinder) service;
            clientBinder.startConnect();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

}
