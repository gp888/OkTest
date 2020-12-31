package com.gp.oktest.minivideo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gp.oktest.R;

public class PostVideoFragment extends Fragment {

    public static PostVideoFragment newInstance() {
        PostVideoFragment fragment = new PostVideoFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("videoPath", videoPath);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_post_video, container, false);
        return mRootView;
    }
}
