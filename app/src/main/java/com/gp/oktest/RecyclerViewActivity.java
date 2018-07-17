package com.gp.oktest;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoping on 2017/12/6.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    List<Item> data = new ArrayList<>();
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(int i = 0; i < 10; i++) {
            Item item = new Item();
            item.str = i + "";
            item.pic = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1089772378,1178709717&fm=173&s=AC9021DC5212A58EEB1D3863010030E2&w=580&h=330&img.JPEG";
            data.add(item);
        }

        adapter = new RecyclerAdapter(data);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view instanceof Button) {

//                    data.get(0).pic = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1809824937,886401976&fm=173&s=04106033199F44CA54DCA5DE0000C0B3&w=580&h=727&img.JPG";
//                    adapter.notifyItemChanged(0);

                    Item item = new Item();
                    item.str = "33"; item.pic = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1809824937,886401976&fm=173&s=04106033199F44CA54DCA5DE0000C0B3&w=580&h=727&img.JPG";
                    data.add(position + 1,item);
                    adapter.notifyItemInserted(position + 1);
                    //列表从positionStart位置到itemCount数量的列表项进行数据刷新
                    adapter.notifyItemRangeChanged(position, data.size() - position);

//                    adapter.notifyItemRemoved(position);

                    //列表fromPosition位置的数据移到toPosition位置时调用，伴有动画效果
//                    notifyItemMoved(int fromPosition, int toPosition)

                    //列表从positionStart位置到itemCount数量的列表项批量添加数据时调用，伴有动画效果
//                    notifyItemRangeInserted(int positionStart, int itemCount)

                    //列表从positionStart位置到itemCount数量的列表项批量删除数据时调用，伴有动画效果
//                    notifyItemRangeRemoved(int positionStart, int itemCount)

                } else if (view instanceof TextView){
                    Toast.makeText(RecyclerViewActivity.this, "44", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //为RecyclerView添加默认动画效果，测试不写也可以
        mRecyclerView.setItemAnimator(new EidtItemAnimator());//DefaultItemAnimator(), NoAlphaItemAnimator
        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.scrollToPosition(0);
    }
}
