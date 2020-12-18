package com.gp.oktest.minivideo;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.utils.FileUtils;

import java.io.File;

public class MiniVideoActivity extends BaseActivity {

    Button button;
    String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minivideo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.CAMERA}, 5);
        }

        button = findViewById(R.id.btn_record_video);
        videoPath = FileUtils.getFilesPath(this) + "/capturevideo";
        File fpath = new File(videoPath);
        if (!fpath.exists()) {
            fpath.mkdirs();
        }
        Log.d("MiniVideoActivity", videoPath);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MiniVideoRecordActivity.startRecordActivity(videoPath,MiniVideoActivity.this);
                startActivity(intent);
            }
        });
    }
}
