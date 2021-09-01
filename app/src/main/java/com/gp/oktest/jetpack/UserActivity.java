package com.gp.oktest.jetpack;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gp.oktest.R;

public class UserActivity extends AppCompatActivity {

    TextView tvUserName;
    ProgressBar pbLoading;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取ViewModel实例
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        UserViewModel userViewModel = viewModelProvider.get(UserViewModel.class);
        //观察 用户信息
        userViewModel.getUserLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null) {
                    Toast.makeText(UserActivity.this, "获取user失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                // update ui.
                tvUserName.setText(s);
            }
        });

        userViewModel.getLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                pbLoading.setVisibility(aBoolean? View.VISIBLE:View.GONE);
            }
        });
        //点击按钮获取用户信息
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.getUserInfo();
            }
        });
    }
}

/**

//=======fragment 复用viewModel========

//ViewModel
public class SharedViewModel extends ViewModel {
    //被选中的Item
    private final MutableLiveData<UserContent.UserItem> selected = new MutableLiveData<UserContent.UserItem>();

    public void select(UserContent.UserItem user) {
        selected.setValue(user);
    }
    public LiveData<UserContent.UserItem> getSelected() {
        return selected;
    }
}

//ListFragment
class MyListFragment extends Fragment {
    private SharedViewModel model;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取ViewModel，注意ViewModelProvider实例传入的是宿主Activity
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        adapter.setListner(new MyItemRecyclerViewAdapter.ItemCLickListner(){
            @Override
            public void onClickItem(UserContent.UserItem userItem) {
                model.select(userItem);
            }
        });
    }
}

//DetailFragment
class DetailFragment extends Fragment {

    TextView detail;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取ViewModel,观察被选中的Item
        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), new Observer<UserContent.UserItem>() {
            @Override
            public void onChanged(UserContent.UserItem userItem) {
                //展示详情
                detail.setText(userItem.toString());
            }
        });
    }
}


 **/



