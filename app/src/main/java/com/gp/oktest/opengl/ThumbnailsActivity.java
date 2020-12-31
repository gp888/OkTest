package com.gp.oktest.opengl;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gp.oktest.R;
import com.gp.oktest.minivideo.PostVideoFragment;
import com.gp.oktest.minivideo.SelectCoverFragment;


import java.io.File;

public class ThumbnailsActivity extends AppCompatActivity  {


    private String mVideoPath;
    private SelectCoverFragment selectCoverFragment;
    private PostVideoFragment postVideoFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_thumbnails);
        getPath();

        selectCoverFragment = (SelectCoverFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (null == selectCoverFragment) {
            selectCoverFragment = SelectCoverFragment.newInstance(mVideoPath);
        }
        addFragment(getSupportFragmentManager(), selectCoverFragment,
                R.id.content_frame);
    }


    private void getPath() {
        mVideoPath = getIntent().getStringExtra("mp4Path");

        if (TextUtils.isEmpty(mVideoPath) || !new File(mVideoPath).exists()) {
            Toast.makeText(this, "视频文件不存在", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public void addFragment(@NonNull FragmentManager fragmentManager,
                            @NonNull Fragment fragment,
                            int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void removeFragment(@NonNull FragmentManager fragmentManager,
                               @NonNull Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    public void replacePostFragment(){
        removeFragment(getSupportFragmentManager(), selectCoverFragment);
//        postVideoFragment = (PostVideoFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (null == postVideoFragment) {
            postVideoFragment = PostVideoFragment.newInstance();
        }
        addFragment(getSupportFragmentManager(), postVideoFragment,
                R.id.content_frame);
    }
}
