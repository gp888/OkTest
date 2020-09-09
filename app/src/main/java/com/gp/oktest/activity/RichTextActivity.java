package com.gp.oktest.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gp.oktest.App;
import com.gp.oktest.R;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

/**
 * 富文本展示
 */
public class RichTextActivity extends AppCompatActivity {

    String richTextStr = "今天，科技发展极大地改善了人类生活的方方面面，使得人们难以脱离科技成果，退回到现代科技之前的生活状态中。但越来越多的人也意识到，一些科技成果常常携带着_______的后果，可能对人类生活产生负面影响<br/><br/><img src=\"https://www.ranvip.com:8099/imgs/2d72dc3b77ea02f4677526250b11f5dc/2252.jpg\" style=\"width:1.0500028in;height:1.5416666in;vertical-align:text-bottom;\">";
    String richTextStr1 = "今天，科技发展极大地改善了人类生活的方方面面，使得人们难以脱离科技成果，退回到现代科技之前的生活状态中<br/><img style=\"width: 2.25in; height: 2.791in; vertical-align: text-bottom;\" src=\"https://www.ranvip.com:8099/imgs/3b1c1ff1353f6b793c3140e7559fe9c6/29366.png\"/><br/><br/>";


    String str2 = "今天，科技发展极大地改善了人类生活的方方面面，使得人们难以脱离科技成果，退回到现代科技之前的生活状态中。但越来越多的人也意识到，一些科技成果常常携带着_______的后果，可能对人类生活产生负面影响。<br/>填入画横线处最恰当的一项是：<img src=\"https://app.ranvip.com:7071/2020/8/28/0db074181fa34afabc8b9a18224fb30d.png\" title=\"image.png\" alt=\"image.png\" width=\"177\" height=\"101\" style=\"width: 177px; height: 101px;\"/>";


    String xiaoweixin = "的模范。填入画横线处最恰当的一项是：<img src=\"https://app.ranvip.com:7071/2020/8/28/091df2e88d574a41864a94702c03f30b.png\" title=\"image.png\" alt=\"image.png\" width=\"439\" height=\"209\" style=\"width: 439px; height: 209px;\"/>";

    String haha = "这样的统计图用什么图来表示比较恰当？<br/>完善扇形统计图<br/><img src=\"https://app.ranvip.com:7071/2020/9/5/3ddfe7e0a6b543c0acf31c05fabb0b4a.png\" title=\"2a9179154ad4031173a8b1868d6462f.png\" alt=\"2a9179154ad4031173a8b1868d6462f.png\" width=\"440\" height=\"279\" style=\"width: 440px; height: 279px;\"/><br/>5.经历扇形统计图生成过程。<br/>6.观察扇形统计图并思考：图中整个圆表示什么？各个扇形大小与什么有关系？<br/>7.归纳扇形统计图的特点和作用。<br/>【设计理由】从开放式的问题出发，培养学生发现和提出问题的能力，采用引导学生逐步思考探究的方式，帮助学生积累数学活动经验，在探究扇形统计的特点和作用的过程中，感受扇形统计图的价值，体会统计方法与统计思想。<br/>巩固应用，内化提高<br/>完成教科书上的“做一做”。<br/>整理归纳，反思提升<br/>这节课我们学习了哪些知识？";

    String newStr = "物产丰富的世界！<br/><br/> <img src=\"https://app.ranvip.com:7071/2020/9/8/65ac71dd786e4862a4ff9ed58b730c4b.png\" title=\"26.png\" alt=\"26.png\" width=\"595\" height=\"172\" style=\"width: 595px; height: 172px;\"/>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
//        ImmersionBar.with(this).statusBarDarkFont(true).init();

        RichText.initCacheDir(App.Companion.getGlobalContext());
        TextView tv = findViewById(R.id.tv);
        TextView tv1 = findViewById(R.id.tv1);


        RichText.from(newStr)
                .autoFix(true)
                .singleLoad(false)
                .cache(CacheType.all)
                .scaleType(ImageHolder.ScaleType.none)
                .into(tv);

        RichText.fromHtml(haha)
                .autoFix(false)
                .resetSize(false)
                .scaleType(ImageHolder.ScaleType.none)
                .into(tv1);
    }
}
