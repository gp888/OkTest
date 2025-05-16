package com.gp.oktest.opengl

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.databinding.ActivityCameraRecordBinding
import com.gp.oktest.utils.ToastUtil
import java.io.File

class CameraRecordActivity : BaseActivity<ActivityCameraRecordBinding>(){


    private val REQUEST_VIDEO_CROP_CODE = 233
    private val REQUEST_PERMISSIONS = 333
    private val REQUEST_PERMISSIONS_SETTINGS = 444
    private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_record)
    }


    fun onClick(view: View){
        when(view.id){
            R.id.camera_surfaceview ->{
                startActivity(Intent(this, SurfacePreviewActivity::class.java))
            }
            R.id.gl_activity ->
                startActivity(Intent(this, GLActivity::class.java))
            R.id.camera_textureview ->
                startActivity(Intent(this, CameraTextureViewShowActivity::class.java))
            R.id.camera_gl_Surfaceview ->
                startActivity(Intent(this, CameraGlSurfaceShowActivity::class.java))
            R.id.mediastore_pick ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val i = ContextCompat.checkSelfPermission(this, PERMISSIONS_STORAGE.get(0))
                    if (i != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSIONS)
                    } else {
                        select()
                    }
                } else {
                    select()
                }

        }
    }

    fun select(){
        startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI), REQUEST_VIDEO_CROP_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_VIDEO_CROP_CODE) {
            if (resultCode == RESULT_OK) {
                val uri = data!!.data
                val cursor = if (contentResolver == null) null else if (uri == null) null
                else contentResolver.query(uri, null, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    if (videoPath.endsWith(".mp4")) {
                        if (requestCode == REQUEST_VIDEO_CROP_CODE) {
                            ToastUtil.showToastLong(videoPath)
                            val intent = Intent(this, ThumbnailsActivity::class.java)
                            intent.putExtra("mp4Path", videoPath)
                            startActivity(intent)
                        } else {

                        }
                    }
                }
                cursor.close()
            }
        } else if (requestCode == REQUEST_PERMISSIONS_SETTINGS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val i = ContextCompat.checkSelfPermission(this, PERMISSIONS_STORAGE.get(0))
                if (i != PackageManager.PERMISSION_GRANTED) {
//                    goToAppSetting()
                    ToastUtil.showToastShort("需要权限")
                } else {
                   select()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val b = shouldShowRequestPermissionRationale(permissions[0])
                    if (!b) {
                        goToAppSetting()
                    }
                } else {
                    select()
                }
            }
        }
    }

    private fun goToAppSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, REQUEST_PERMISSIONS_SETTINGS)
    }

}