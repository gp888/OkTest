package com.gp.oktest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by guoping on 2018/1/5.
 */

public class ThemeBaseActivity extends AppCompatActivity {
    private Button changeTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        changeTheme = findViewById(R.id.changeTheme);
        changeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThemeChooseDialog();
            }
        });
    }

    private void initTheme() {
        ThemeUtils.Theme theme = ThemeUtils.getCurrentTheme(this);
        ThemeUtils.changTheme(this, theme);
    }


    private void showThemeChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ChangeThemeDialog);
        builder.setTitle("更换主题");
        Integer[] res = new Integer[]{R.drawable.red_round, R.drawable.brown_round, R.drawable.blue_round,
                R.drawable.blue_grey_round, R.drawable.yellow_round, R.drawable.deep_purple_round,
                R.drawable.pink_round, R.drawable.green_round, R.drawable.deep_orange_round,
                R.drawable.grey_round, R.drawable.cyan_round, R.drawable.amber_round};
        List<Integer> list = Arrays.asList(res);
        ColorsListAdapter adapter = new ColorsListAdapter(this, list);
        adapter.setCheckItem(0);
        GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.colors_panel_layout, null);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);
        builder.setView(gridView);
        final AlertDialog dialog = builder.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        int value = ThemeUtils.getCurrentTheme(ThemeBaseActivity.this).getIntValue();
                        Log.d("wxl", "value==" + value);
                        if (value != position) {
                            PreferenceUtils.setPreferenceInt(ThemeBaseActivity.this, "change_theme_key", position);
                            recreate();
                        }
                    }
                }
        );
    }
    public class ColorsListAdapter extends BaseAdapter {
        private int checkItem;
        private Context context;
        private List<Integer> list;
        public ColorsListAdapter(Context context, List<Integer> list) {
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.colors_image_layout, null);
                holder = new Holder();
                holder.imageView1 = (ImageView) convertView.findViewById(R.id.img_1);
                holder.imageView2 = (ImageView) convertView.findViewById(R.id.img_2);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.imageView1.setImageResource(list.get(position));
            if (checkItem == position) {
                holder.imageView2.setImageResource(R.drawable.ic_chat_face1);
            }
            return convertView;
        }
        public void setCheckItem(int checkItem) {
            this.checkItem = checkItem;
        }
        class Holder {
            ImageView imageView1;
            ImageView imageView2;
        }
    }


}
