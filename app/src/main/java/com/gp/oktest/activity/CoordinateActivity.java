package com.gp.oktest.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

public class CoordinateActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);




    }

    private void getVideoMedia(){
        ContentResolver contentResolver = getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DATA};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for(int counter = 0; counter < fileNum; counter++){
            //MediaStore.Video.Media.DATA
            Log.w(TAG, "----------------------file is: " + cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)) );
            cursor.moveToNext();
        }
        cursor.close();
    }

    private void getAudioMedia(){
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int counter = cursor.getCount();
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

        Log.w(TAG, "------------before looping, title = " + title);
        for(int j = 0 ; j < counter; j++){
            Log.w(TAG, "-----------title = "
                    + cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));

            cursor.moveToNext();

        }
        cursor.close();
    }
}
