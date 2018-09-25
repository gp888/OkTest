package com.gp.oktest;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoping on 2018/3/27.
 */

public class KeyBoardAvtivity extends AppCompatActivity {

    @BindView(R.id.ed)
    EditText ed;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
       ButterKnife.bind(this);


       Keyboard keyboradNumber = new Keyboard(this, R.xml.keyboard);
       ed.setShowSoftInputOnFocus(false);

    }

}
