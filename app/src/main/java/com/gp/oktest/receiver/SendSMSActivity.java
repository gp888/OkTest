package com.gp.oktest.receiver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

public class SendSMSActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTextMessage();
            }
        });

        String[] permissions = {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permissions, 100);
        }

    }

    public void sendTextMessage(){
        SMSMethod.getInstance(this).SendMessage("15510232025","收到给我恢复一下，高");
    }
    //如果太长，就分开发送
    public void sendMultipartTextMessage(){
        SMSMethod.getInstance(this).SendMessage2("15510232025", "收到给我恢复一下，高");
    }


    @Override
    protected void onDestroy() {
        SMSMethod.getInstance(this).unregisterReceiver();
        super.onDestroy();
    }


    /**
     * 调用系统电话app打电话，不需要申请权限
     * @param phoneNumber
     * @param message
     */
    public void doSendSMSTo(String phoneNumber,String message){
        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }
}
