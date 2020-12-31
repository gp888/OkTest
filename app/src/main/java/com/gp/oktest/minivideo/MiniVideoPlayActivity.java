package com.gp.oktest.minivideo;

import android.content.Intent;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.gp.oktest.R;
import com.gp.oktest.opengl.ThumbnailsActivity;

import java.io.File;

public class MiniVideoPlayActivity extends AppCompatActivity {

    VideoView videoView;
    Button confirm;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = findViewById(R.id.libPlayVideo_videoView);
        confirm = findViewById(R.id.confirm);
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (isFirst) {
                    isFirst = false;
                    Toast.makeText(MiniVideoPlayActivity.this, "播放该视频异常", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        String mVideoPath = getIntent().getStringExtra(MiniVideoRecordActivity.kVideoSavePath);
        File file = new File(mVideoPath);
        if (file.exists()) {
            videoView.setVideoPath(file.getAbsolutePath());
            videoView.start();
            setLoop(file.getAbsolutePath());
        } else {
            Log.e("tag","not found video " + mVideoPath);
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniVideoPlayActivity.this, ThumbnailsActivity.class);
                intent.putExtra("mp4Path", mVideoPath);
                startActivity(intent);
            }
        });
    }

    public void setLoop(final String videoPath) {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);

            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(videoPath);
                videoView.start();
            }
        });
    }
}