package com.gp.oktest.handlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * <br> HandlerThread 示例程序
 * <p>
 * <br> Created by shixinzhang on 17/6/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class HandlerThreadActivity extends BaseActivity implements Handler.Callback {

    @BindView(R.id.tv_start_msg)
    TextView mTvStartMsg;
    @BindView(R.id.tv_finish_msg)
    TextView mTvFinishMsg;
    @BindView(R.id.btn_start_download)
    Button mBtnStartDownload;

    private Handler mUIHandler;
    private DownloadThread mDownloadThread;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlerthread);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mUIHandler = new Handler(this);
        mDownloadThread = new DownloadThread("下载线程");
        mDownloadThread.setUIHandler(mUIHandler);
        mDownloadThread.setDownloadUrls("http://pan.baidu.com/s/1qYc3EDQ",
                "http://bbs.005.tv/thread-589833-1-1.html", "http://list.youku.com/show/id_zc51e1d547a5b11e2a19e.html?");
    }

    @OnClick(R.id.btn_start_download)
    public void startDownload() {
        mDownloadThread.start();
        mBtnStartDownload.setText("正在下载");
        mBtnStartDownload.setEnabled(false);
    }

    //主线程中的 Handler 处理消息的方法
    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case DownloadThread.TYPE_FINISHED:
                mTvFinishMsg.setText(mTvFinishMsg.getText().toString() + "\n " + msg.obj);
                break;
            case DownloadThread.TYPE_START:
                mTvStartMsg.setText(mTvStartMsg.getText().toString() + "\n " + msg.obj);
                break;
        }
        return true;
    }
}
