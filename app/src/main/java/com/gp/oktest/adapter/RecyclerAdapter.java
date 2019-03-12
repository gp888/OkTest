package com.gp.oktest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gp.oktest.model.RecyclerItem;
import com.gp.oktest.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private List<RecyclerItem> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public RecyclerAdapter(List<RecyclerItem> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //视图中 itemView 的下标
        final int pos = holder.getLayoutPosition();
        //Adapter数据集data元素 的下标.
//        final int pos = holder.getAdapterPosition();

        holder.str.setText(data.get(position).str);
        Glide.with(holder.pic.getContext())
                .load(data.get(position).pic)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.pic);
         holder.itemView.setTag(position);

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, pos);
                }
            }
        });
        holder.str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, pos);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView str;
        Button bt;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            str = itemView.findViewById(R.id.str);
            bt = itemView.findViewById(R.id.bt);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
