package com.gp.oktest.dialog;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gp.oktest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 连续7天每天签到
 */
public class EveryDaySigninAdapter extends RecyclerView.Adapter<EveryDaySigninAdapter.SignInViewHolder>{

    public static int ITEM_TYPE_1 = 1;
    public static int ITEM_TYPE_2 = 2;
    private OnItemClickListener onItemClickListener;

    @Override
    public SignInViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.signin_item, parent, false);
        return new SignInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SignInViewHolder holder, int position) {
        holder.tv1.setText("第一天");
        holder.tv2.setText("已领取");
        if(position == 6){
            holder.image.setImageResource(R.drawable.icon_day7);
        }
        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(SignInViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class SignInViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.image)
        ImageView image;

        public SignInViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 6) return ITEM_TYPE_2;
        return ITEM_TYPE_1;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
