package com.gp.oktest.activity;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gp.oktest.R;

/**
 * Created by guoping on 2017/12/11.
 */

public class MoveViewActivity extends AppCompatActivity {

    EditText editText;
    ImageView vectorImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_move);

        editText  = findViewById(R.id.emoji);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (int i = 0; i < s.length(); i++) {
                    char sChar = s.charAt(i);
                    if (!isEmojiCharacter(sChar)) {
                        Toast.makeText(MoveViewActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initVectorAnim();
    }

    /**
     * 是否包含表情,如果不包含返回true
     * @param codePoint
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        boolean isContains = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        if(isContains) {
            return true;
        }
        return false;
    }


    private void initVectorAnim() {
        vectorImage  = findViewById(R.id.vectorImage);
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(
                this, R.drawable.vector_anim
        );
        vectorImage.setImageDrawable(animatedVectorDrawableCompat);
        ((Animatable) vectorImage.getDrawable()).start();
    }
}
