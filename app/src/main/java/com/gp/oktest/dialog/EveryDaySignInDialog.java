package com.gp.oktest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gp.oktest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gp.oktest.dialog.EveryDaySigninAdapter.ITEM_TYPE_1;
import static com.gp.oktest.dialog.EveryDaySigninAdapter.ITEM_TYPE_2;

/**
 * 签到dialog
 */
public class EveryDaySignInDialog extends Dialog {

    private Context context;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public EveryDaySignInDialog(Context context) {
        super(context, R.style.li_gAlertDialogTheme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_everyday_signin);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        GridLayoutManager manager = new GridLayoutManager(context, 4);
        EveryDaySigninAdapter adapter = new EveryDaySigninAdapter();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize = 1;
                if (adapter.getItemViewType(position) == ITEM_TYPE_2) {
                    spanSize = 2;
                } else if (adapter.getItemViewType(position) == ITEM_TYPE_1) {
                    spanSize = 1;
                }
                return spanSize;
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int gap =6;
                int gap1 = 3;
                outRect.top = gap;
                outRect.bottom = gap;
                outRect.left = gap1;
                outRect.right = gap1;
            }
        });
        recyclerView.setAdapter(adapter);
    }
    @OnClick({R.id.dismiss_img})
    public void onViewClicked(View view){
        int id = view.getId();
        switch (id){
            case R.id.dismiss_img:
                dismiss();
                break;
        }
    }
}
