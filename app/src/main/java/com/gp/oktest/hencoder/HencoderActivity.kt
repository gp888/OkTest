package com.gp.oktest.hencoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gp.oktest.R
import com.gp.oktest.utils.StatusBarUtil
import org.jetbrains.anko.find

class HencoderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hencoder)
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.makeStatusBarTransparent(this, findViewById(R.id.root), findViewById(R.id.sportView));
    }
}