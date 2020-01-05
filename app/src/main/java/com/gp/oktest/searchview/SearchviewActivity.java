package com.gp.oktest.searchview;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.gp.oktest.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SearchviewActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private AutoCompleteTextView mAutoCompleteTextView;//搜索输入框
    private ImageView mDeleteButton;//搜索框中的删除按钮
    GridView mGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);

        initView();
        initData();
        setListener();
    }

    private void initView(){
        mGrid = findViewById(R.id.grid_view);

        mSearchView=findViewById(R.id.view_search);
        mAutoCompleteTextView=mSearchView.findViewById(R.id.search_src_text);
        mDeleteButton=mSearchView.findViewById(R.id.search_close_btn);
    }

    private void initData(){
        mSearchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        //1:回车
        //2:前往
        //3:搜索
        //4:发送
        //5:下一項
        //6:完成
        mSearchView.setImeOptions(6);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
//        mSearchView.setInputType(1);//设置输入类型
//        mSearchView.setMaxWidth(200);//设置最大宽度
        mSearchView.setQueryHint("请输入");//设置查询提示字符串
//        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
        //设置SearchView下划线透明
        setUnderLinetransparent(mSearchView);
    }

    private void setListener(){

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("TAG","=====query="+query);
                setAdapter();

                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("TAG","=====newText="+newText);
                return false;
            }
        });
    }

    /**设置SearchView下划线透明**/
    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(){
        mSearchView.postDelayed((Runnable) () -> {
            List<PicModel> mData = new ArrayList<>();

            for(int i = 0; i< 50; i++) {
                PicModel m = new PicModel();
                m.url = "https://tva1.sinaimg.cn/large/0080xEK2gy1g8zv2gs54gj30u01110wm.jpg";
                m.text = i + "hehe";
                mData.add(m);
            }

            SearchAdapter adapter = new SearchAdapter(SearchviewActivity.this, mData);
            mGrid.setAdapter(adapter);

        }, 1000);


    }
}
