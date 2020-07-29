package com.gp.oktest.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.utils.ToastUtil;
import com.gp.testlibrary.FileProvider7Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileProvider7Activity extends BaseActivity {

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final int REQ_PERMISSION_CODE_SDCARD = 0X111;
    private static final int REQ_PERMISSION_CODE_TAKE_PHOTO = 0X112;
    private static final int REQ_INSTALL_PERMISS_CODE = 0X113;
    private String mCurrentPhotoPath;

    @BindView(R.id.iv)
    ImageView iv;

    @BindView(R.id.takePhoto)
    Button takephoto;

    @BindView(R.id.installApp)
    Button installApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileprovider);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.takePhoto, R.id.installApp})
    public void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.takePhoto:
                takePhotoNoCompress(v);
                break;
            case R.id.installApp:
                installApk(v);
                break;
            default:
                break;
        }
    }

    public void installApk(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION_CODE_SDCARD);
        } else {
            installApk8();
        }
    }

    private void installApk8() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(getPackageManager().canRequestPackageInstalls()){
                installApp();
            } else{
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                startActivityForResult(intent, REQ_INSTALL_PERMISS_CODE);
            }
        } else {
            installApp();
        }
    }

    private void installApp() {
        File file = new File(Environment.getExternalStorageDirectory(), "znt.apk");
        if (!file.exists()) {
            ToastUtil.showToastShort("no apk");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7Util.setIntentDataAndType(this, intent, "application/vnd.android.package-archive", file, true);
        startActivity(intent);
    }


    public void takePhotoNoCompress(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION_CODE_TAKE_PHOTO);
        } else {
            takePhotoNoCompress();
        }
    }

    private void takePhotoNoCompress() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = FileProvider7Util.getUriForFile(this, file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                iv.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                //8.0未知应用来源权限
            } else if (requestCode == REQ_INSTALL_PERMISS_CODE) {
                installApp();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQ_PERMISSION_CODE_SDCARD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                installApk8();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQ_PERMISSION_CODE_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoNoCompress();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
