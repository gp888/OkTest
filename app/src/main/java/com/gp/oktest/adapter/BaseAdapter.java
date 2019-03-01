package com.gp.oktest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gp.oktest.model.BaseModel;

import java.util.ArrayList;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    protected onRVItemClick clickListener;
    protected int selectPosition;
    protected ArrayList<? extends BaseModel> mData;
    protected Context ctx;

    public void setData(ArrayList<? extends BaseModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setClickListener(onRVItemClick clickListener) {
        this.clickListener = clickListener;
    }

    public int getSelectPostion() {
        return selectPosition;
    }

    public interface onRVItemClick {
        void onClick(View view, int position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
