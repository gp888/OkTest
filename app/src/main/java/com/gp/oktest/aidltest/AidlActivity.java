package com.gp.oktest.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.gp.oktest.IMyAidl;
import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;


//https://blog.csdn.net/u011240877/article/details/72765136
public class AidlActivity extends BaseActivity {

    private IMyAidl mAidl;

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.bt)
    public void addPerson() {
        Random random = new Random();
        Person person = new Person("shixin" + random.nextInt(10));

        try {
            mAidl.addPerson(person);
            List<Person> personList = mAidl.getPersonList();
            tv.setText(personList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            mAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAidl = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidltest);

        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);
    }
}
