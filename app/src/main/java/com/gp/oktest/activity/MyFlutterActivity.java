package com.gp.oktest.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;


public class MyFlutterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter);
//        final FlutterView flutterView = Flutter.createView(
//                this,
//                getLifecycle(),
//                "route2"
//        );
//        final FrameLayout layout = findViewById(R.id.flutter_container);
//        layout.addView(flutterView);
//        final FlutterView.FirstFrameListener[] listeners = new FlutterView.FirstFrameListener[1];
//        listeners[0] = new FlutterView.FirstFrameListener() {
//            @Override
//            public void onFirstFrame() {
//                layout.setVisibility(View.VISIBLE);
//            }
//        };
//        flutterView.addFirstFrameListener(listeners[0]);
    }
}
