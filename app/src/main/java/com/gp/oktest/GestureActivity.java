package com.gp.oktest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gp.oktest.base.BaseActivity;

public class GestureActivity extends BaseActivity {
    Button bt;
    RelativeLayout rl;
    TextView tv;
    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        rl = findViewById(R.id.rl);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        bt.setBackgroundColor(Color.RED);

        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("markgpp", "onSingleTapConfirmed:");
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("markgpp", "onDoubleTap");
                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d("markgpp", "onLongPress");
            }

        });



//        rl.setOnTouchListener(onTouchListener);
//        bt.setOnTouchListener(onTouchListener);
//        tv.setOnTouchListener(onTouchListener);

        rl.setOnLongClickListener(onLongClickListener);
        bt.setOnLongClickListener(onLongClickListener);
        tv.setOnLongClickListener(onLongClickListener);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.rl:

                    break;
                case R.id.tv:

                    break;
            }
            return detector.onTouchEvent(event);
        }
    };


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt:
                    if (v.isPressed()) {
                        Log.d("markgpp", "bt.onClick");
                    } else {
                        bt.setBackgroundColor(Color.BLUE);
                    }
                    break;
                case R.id.tv:
                    Log.d("markgpp", "tv.onClick");
                    break;
                default:
                    break;
            }
        }
    };

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.bt:
                    Log.d("markgpp", "bt.onLongClick");
                    break;
                case R.id.tv:
                    Log.d("markgpp", "tv.onLongClick");
                    break;
                case R.id.rl:
                    Log.d("markgpp", "rl.onLongClick");
                    break;
                default:
                    break;
            }
            return true;
        }
    };
}
