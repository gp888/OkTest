package com.gp.oktest;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

public class MyPopupMenu extends PopupMenu{

    public MyPopupMenu(Context context, View anchor) {
        super(context, anchor);
    }

    public MyPopupMenu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
    }

    public MyPopupMenu(Context context, View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {
        super(context, anchor, gravity, popupStyleAttr, popupStyleRes);
    }

//构造
//    // 显示固定的View对象
//    MyPopupMenu (Context context, View anchor);
//    // 显示在View的左下方还是右下方. 默认左下方
//    MyPopupMenu (Context context, View anchor, int gravity);
//    MyPopupMenu (Context context, View anchor, int gravity, int popupStyleAttr, int popupStyleRes);



    //填充
//    popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu()).

//    Android4.0(API14)后新增的简化方法:
//    void inflate (int menuRes) // 直接传入Menu文件id即可

//    显示和隐藏
//    void show () // 显示
//    void dismiss ()  // 隐藏


//    PopupMenu关闭事件监听
//    void setOnDismissListener (PopupMenu.OnDismissListener listener)
//    菜单选项事件监听
//    void setOnMenuItemClickListener (PopupMenu.OnMenuItemClickListener listener)
//    滑动开打
//    这个方法蛮有意思的. 可以通过拖拽显示固定的View来打开PopupMenu.
//
//            View.OnTouchListener getDragToOpenListener ()



//    该方法PopupWindow同样有, 支持Gravity.LEFT和Gravity.RIGHT, 这类setGravity的方法基本上都是Android6.0后添加的.
//
//    void setGravity (int gravity)
//    int getGravity ()
}
