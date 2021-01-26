package com.gp.oktest.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.TextView;

import com.gp.oktest.BuildConfig;
import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.utils.DeviceUtils;
import com.gp.oktest.utils.RadiusBackgroundSpan;
import com.gp.oktest.utils.SpanUtils;
import com.gp.oktest.utils.ToastUtil;
import com.gp.oktest.view.IconTextSpan;
import com.gp.oktest.view.LinearGradientFontSpan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpannableActivity extends BaseActivity {

    @BindView(R.id.spannableText)
    TextView spannText;

    @BindView(R.id.tvHtml)
    TextView tvHtml;

    @BindView(R.id.test)
    TextView spanTest;

    @BindView(R.id.test1)
    TextView spanTest1;

    int lineHeight = 40;

    int textSize = 40;

    float    density;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);
        ButterKnife.bind(this);

        density = getResources().getDisplayMetrics().density;

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtil.showToastShort("事件触发了");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        spannText.setText(new SpanUtils()
                .appendLine("SpanUtils").setBackgroundColor(Color.LTGRAY).setBold().setForegroundColor(Color.YELLOW).setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("前景色").setForegroundColor(Color.GREEN)
                .appendLine("背景色").setBackgroundColor(Color.LTGRAY)
                .appendLine("行高顶部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
                .appendLine("行高居中对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
                .appendLine("行高底部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("测试段落缩，首行缩进两字，其他行不缩进虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎虎").setLeadingMargin((int) textSize * 2, 10).setBackgroundColor(Color.GREEN)
                .appendLine("测试引用，后面的字是为了凑到两行的效果呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼呼").setQuoteColor(Color.GREEN, 10, 10).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试列表项，后面的字是为了凑到两行的效果呼呼呼呼呼呼呼呼u虎虎虎虎虎虎呼呼呼呼呼").setBullet(Color.GREEN, 20, 10).setBackgroundColor(Color.LTGRAY).setBackgroundColor(Color.GREEN)
//                .appendLine("测试图标文字顶部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.LTGRAY)
//                .appendLine("测试图标文字居中对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.GREEN)
//                .appendLine("测试图标文字底部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.LTGRAY)
//                .appendLine("测试图标顶部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
//                .appendLine("测试图标居中对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
//                .appendLine("测试图标底部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("32dp字体").setFontSize(32, true)
                .appendLine("2倍字体").setFontProportion(2)
                .appendLine("横向2倍字体").setFontXProportion(1.5f)
                .appendLine("删除线").setStrikethrough()
                .appendLine("下划线").setUnderline()
                .append("测试").appendLine("上标").setSuperscript()
                .append("测试").appendLine("下标").setSubscript()
                .appendLine("粗体").setBold()
                .appendLine("斜体").setItalic()
                .appendLine("粗斜体").setBoldItalic()
                .appendLine("monospace字体").setFontFamily("monospace")
                .appendLine("自定义字体").setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dnmbhs.ttf"))
                .appendLine("相反对齐").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .appendLine("居中对齐").setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("正常对齐").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试").appendLine("点击事件").setClickSpan(clickableSpan)
                .append("测试").appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试").appendLine("模糊").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .appendLine("颜色渐变").setShader(new LinearGradient(0, 0,
                        64 * density * 4, 0,
                        getResources().getIntArray(R.array.rainbow),
                        null,
                        Shader.TileMode.REPEAT)).setFontSize(64, true)
                .appendLine("图片着色").setFontSize(64, true).setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.span_cheetah),
                        Shader.TileMode.REPEAT,
                        Shader.TileMode.REPEAT))
                .appendLine("阴影效果").setFontSize(64, true).setBackgroundColor(Color.BLACK).setShadow(24, 8, 8, Color.WHITE)
                .append("测试小图对齐").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_TOP)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_CENTER)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_BASELINE)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_BOTTOM)
                .appendLine("end").setBackgroundColor(Color.LTGRAY)
                .append("测试大图字体顶部对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_TOP)
                .appendLine()
                .append("测试大图字体居中对齐").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_CENTER)
                .appendLine()
                .append("测试大图字体底部对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_BOTTOM)
                .appendLine()
                .append("测试空格").appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN).appendSpace(100).appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN)
                .create());


        String html = getString(R.string.html);
        /*让链接可点击*/
        tvHtml.setMovementMethod(LinkMovementMethod.getInstance());
        /*ResouroceImageGetter用来处理TextView中的图片*/
        tvHtml.setText(Html.fromHtml(html, new ResouroceImageGetter(this), null));




        spanTest.setTextColor(getResources().getColor(R.color.blue));
        IconTextSpan iconTextSpan = new IconTextSpan(this, R.color.rainbow_blue, "计酸");
        SpannableString spanna = new SpannableString("啊啊哈哈哈诶诶");
        spanna.setSpan(iconTextSpan, 3, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spanTest.setText(spanna);


        LinearGradientFontSpan span0 = new LinearGradientFontSpan(Color.GRAY, Color.LTGRAY);
        SpannableString spannableString = new SpannableString("啊啊哈哈哈诶诶");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F9374B")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span0 , 2 , 5 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE) , 2 , 5 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F86648")), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTest.setText(spannableString);



        RadiusBackgroundSpan span1 = new RadiusBackgroundSpan(Color.RED, Color.GREEN, DeviceUtils.dip2px(2));
        RadiusBackgroundSpan span2 = new RadiusBackgroundSpan(Color.parseColor("#F9374B"), Color.parseColor("#F86648"), DeviceUtils.dip2px(2));
        String str = String.format(getString(R.string.newcomer_countdown), "6天07:44:30");
        int start = str.indexOf("天");
        int start1 = str.indexOf(":");
        int start2 = str.lastIndexOf(":");
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tvc)), 0, start - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span0, start - 1, start + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start1, start1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(span1, start1 + 1, start1 + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start2, start2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(span2, start2+ 1, start2 + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), start2 + 1, start2 + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTest1.setText(builder);
    }

    private static class ResouroceImageGetter implements Html.ImageGetter {
        Context context;
        public ResouroceImageGetter(Context context) {
            this.context = context;
        }

        public Drawable getDrawable(String source) {
            int path = context.getResources().getIdentifier(source, "drawable", BuildConfig.APPLICATION_ID);
            Drawable drawable = context.getResources().getDrawable(path);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    }

    private void testRemove() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        for (int i = 0; i < list.size(); i++) {
            if ("A".equals(list.get(i))) {
                list.remove("A");
            }
        }
    }

    private static class MySpan implements LineBackgroundSpan {
        private final int color;

        public MySpan(int color) {
            this.color = color;
        }

        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
            final int paintColor = p.getColor();
            p.setColor(color);
            c.drawRect(new Rect(left, top, right, bottom), p);
            p.setColor(paintColor);
        }
    }

//    @ColorInt
    class MyChractor extends CharacterStyle {

        @Override
        public void updateDrawState(TextPaint tp) {
//            tp.bgColor
//            tp.setShader()
        }
    }



    private void startAnimation(TextView textView) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null && drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }

    public static float GetTextWidth(String text, float Size) { // 第一个参数是要计算的字符串，第二个参数是字体大小
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(Size);
        return FontPaint.measureText(text);
    }

    public static Rect getTextRect(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length() - 1, rect);
        return rect;
    }

}
