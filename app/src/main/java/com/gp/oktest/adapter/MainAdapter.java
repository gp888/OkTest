package com.gp.oktest.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gp.oktest.R;
import com.gp.oktest.model.TypeBean;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter<MainAdapter.MainHolder> {

    public MainAdapter(Context ctx, ArrayList<TypeBean> mData) {
        this.ctx = ctx;
        this.mData = mData;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainHolder holder = new MainHolder(LayoutInflater.from(ctx)
                .inflate(R.layout.item_main, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder baseHolder, final int position) {
        MainHolder holder = (MainHolder) baseHolder;
        TypeBean model = (TypeBean) mData.get(position);
        holder.title.setText(model.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, position);
            }
        });
    }

    class MainHolder extends RecyclerView.ViewHolder {
        TextView title;
        public MainHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_tv);
        }
    }
}
