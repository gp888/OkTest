package com.gp.oktest.jetpack.databinding;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gp.oktest.R;
import com.gp.oktest.databinding.ActivityBindingListBinding;
import com.gp.oktest.databinding.ActivityListBinding;
import com.gp.oktest.databinding.ItemUserBinding;
import com.gp.oktest.model.User;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ActivityBindingListBinding mViewDataBinding;
    private static UserListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding_list);

        mViewDataBinding.rvUserList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter = new UserListAdapter(getUserList());
        mViewDataBinding.rvUserList.setAdapter(mAdapter);

        mViewDataBinding.setClickPresenter(new ClickPresenter());
    }

    //这里是假装 调用ViewModel能力 获取用户数据
    private List<User> getUserList() {
        List<User> list = new ArrayList<>();
        list.add(new User("小明","Lv1"));
        list.add(new User("小红","Lv2"));
        list.add(new User("小q","Lv3"));
        list.add(new User("小a","Lv4"));
        return list;
    }

    //点击监听处理
    public class ClickPresenter {
        public void addUser(View view) {
            Toast.makeText(ListActivity.this, "addUser", Toast.LENGTH_SHORT).show();
            mAdapter.addData(new User("小z","Lv5"));
        }
        public void removeUser(View view) {
            Toast.makeText(ListActivity.this, "removeUser", Toast.LENGTH_SHORT).show();
            mAdapter.remove(0);
        }
    }

    public class User{
        public String name;
        public String level;
        public User(String name, String level){
            this.name = name;
            this.level = level;
        }
    }

    private static class UserListAdapter extends BaseQuickAdapter<User, UserItemViewHolder> {
        public UserListAdapter(List<User> list) {
            super(R.layout.item_user, list);
        }

        @Override
        protected void convert(@NonNull UserItemViewHolder holder, User user) {
            // 精髓所在1，不需要去一个个setText等等
            holder.getItemUserBinding().setUser(user);
            holder.getItemUserBinding().executePendingBindings();

            //当获取的DataBinding不是具体类时，只是ViewDataBinding，那就要使用setVariable了
//            holder.getViewDataBinding().setVariable(BR.user, user);
//            holder.getViewDataBinding().executePendingBindings();
        }
    }

    private static class UserItemViewHolder extends BaseViewHolder {

        // 精髓所在2，只需要持有 binding即可，不用去findViewById
        private final ItemUserBinding binding;
//        private final ViewDataBinding binding2;

        public UserItemViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
//            binding2 = DataBindingUtil.bind(view);
        }

        public ItemUserBinding getItemUserBinding() {
            return binding;
        }

//        public ViewDataBinding getViewDataBinding() {
//            return binding2;
//        }
    }

}
