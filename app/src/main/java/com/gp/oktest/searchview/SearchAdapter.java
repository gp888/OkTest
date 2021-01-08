package com.gp.oktest.searchview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gp.oktest.App;
import com.gp.oktest.GlideApp;
import com.gp.oktest.R;
import com.gp.oktest.utils.DeviceUtils;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private List<PicModel> provinceBeanList;
    private LayoutInflater layoutInflater;

    public SearchAdapter(Context context, List<PicModel> provinceBeanList) {
        this.provinceBeanList = provinceBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return provinceBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return provinceBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_testgrid, null, false);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.text);
            holder.iv = convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PicModel provinceBean = provinceBeanList.get(position);
        if (provinceBean != null) {
            holder.text.setText(provinceBean.text);

            holder.iv.getHeight();


            //todo hehehe
            GlideApp.with(holder.iv.getContext()).load(provinceBean.url)
//                     .placeholder(R.mipmap.ic_launcher)
//                    .error(R.drawable.wzryfx)
                    .listener(new LoggingListener<Drawable>())
                    .into(holder.iv);
//            holder.iv.setImageResource(R.drawable.czech);


            ViewTreeObserver vto = holder.iv.getViewTreeObserver();
            ViewHolder finalHolder = holder;
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    finalHolder.iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Log.e("gp888", finalHolder.iv.getHeight() + "," + finalHolder.iv.getWidth()
                            + "," + DeviceUtils.dip2px(90));
                }
            });


//            ViewHolder finalHolder = holder;
//            Glide.with(holder.iv.getContext()).load(provinceBean.url).downloadOnly(new SimpleTarget<File>() {
//                @Override
//                public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//                    Uri uri = Uri.fromFile(resource);
//                    finalHolder.iv.setImageURI(uri);
//                }
//            });
        }

        return convertView;
    }

    class ViewHolder {
        TextView text;
        ImageView iv;
    }
}