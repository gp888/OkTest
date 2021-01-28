package com.gp.oktest.view;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class LinearGradientFontSpan2 extends CharacterStyle implements UpdateAppearance {


    private int startColor;
    private int endColor;

    public LinearGradientFontSpan2(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    public void updateDrawState(TextPaint tp) {

        LinearGradient lg = new LinearGradient(0, 0, 0, tp.descent() - tp.ascent(),
                startColor,
                endColor,
                Shader.TileMode.CLAMP);
        tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
    }
}
