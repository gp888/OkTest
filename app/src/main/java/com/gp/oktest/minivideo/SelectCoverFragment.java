package com.gp.oktest.minivideo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gp.oktest.R;
import com.gp.oktest.opengl.CallBackImage;
import com.gp.oktest.opengl.ThumbnailsActivity;
import com.gp.oktest.utils.DeviceUtils;
import com.gp.oktest.utils.VideoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SelectCoverFragment extends Fragment implements CallBackImage{

    public static SelectCoverFragment newInstance(String videoPath) {
        SelectCoverFragment fragment = new SelectCoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString("videoPath", videoPath);
        fragment.setArguments(bundle);
        return fragment;
    }
    protected Activity mActivity;
    private String potoPath;
    private String videoPath;
    private List<String> mThumbPath = new ArrayList<String>();
    RecyclerView recyclerView;
    ImageView imageView;
    Button confirm;
    GridAdapter mAdapter;
    Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_select_videocover, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initData();
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recycler);
        imageView = view.findViewById(R.id.imageView);
        confirm = view.findViewById(R.id.confirm);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),10));
        mAdapter = new GridAdapter(view.getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ThumbnailsActivity)mActivity).replacePostFragment();
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        videoPath = bundle.getString("videoPath");
        String basePath = getContext().getExternalFilesDir(null).getPath();
        potoPath = basePath + File.separator + "photos";

        disposable = Observable.create(new ObservableOnSubscribe<List<LocalPhoto>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<LocalPhoto>> emitter) throws Exception {
                List<LocalPhoto> frames = VideoUtils.getFrams(videoPath, potoPath);
                emitter.onNext(frames);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LocalPhoto>>() {
                    @Override
                    public void accept(List<LocalPhoto> strings) throws Exception {
                        if(strings != null && strings.size() > 0) {
                            mAdapter.setmThumbPath(strings);
                            mAdapter.notifyDataSetChanged();
                            Glide.with(getContext()).load(Uri.fromFile(new File(strings.get(0).getPath()))).into(imageView);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void showImage(String path) {
        Glide.with(getContext()).load(Uri.fromFile(new File(path))).into(imageView);
    }

    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

        private Context context;
        List<LocalPhoto> mThumbPath;
        CallBackImage callBackImage;
        private int lastPosition;

        public GridAdapter(Context context){
            this.context = context;

        }

        public void setmThumbPath(List<LocalPhoto> pohotPath) {
            mThumbPath = pohotPath;
        }

        public void setListener(CallBackImage callBackImage) {
            this.callBackImage = callBackImage;
        }

        @io.reactivex.annotations.NonNull
        @Override
        public GridAdapter.GridViewHolder onCreateViewHolder(@io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.item_thumbnail, parent,false));
        }

        @Override
        public void onBindViewHolder(@io.reactivex.annotations.NonNull GridAdapter.GridViewHolder holder, final int position) {

            LocalPhoto photo = mThumbPath.get(position);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int picWith = (dm.widthPixels - DeviceUtils.dip2px(30)) / 10;
            float ratio = photo.getHeight() > photo.getWidth() ? (float)photo.getHeight() / photo.getWidth() : (float)photo.getWidth() / photo.getHeight();
            float picHeight = ratio * picWith;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            params.width = picWith;
            params.height = (int) picHeight;
            holder.imageView.setLayoutParams(params);


            Glide.with(context).load(Uri.fromFile(new File(photo.getPath()))).into(holder.imageView);

            if(photo.isSelect()) {
                holder.imageView.setBackground(context.getDrawable(R.drawable.shape_thumbnail_back));
                int dpvalue = DeviceUtils.dip2px(3);
                holder.imageView.setPadding(dpvalue, dpvalue, dpvalue, dpvalue);
            } else {
                holder.imageView.setBackground(null);
                holder.imageView.setPadding(0, 0, 0, 0);
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mThumbPath.get(lastPosition).setSelect(false);
                    LocalPhoto photo1 = mThumbPath.get(position);
                    callBackImage.showImage(photo1.getPath());
                    photo1.setSelect(true);
                    notifyDataSetChanged();
                    lastPosition = position;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mThumbPath == null ? 0 : mThumbPath.size();
        }

        class GridViewHolder extends RecyclerView.ViewHolder{

            private ImageView imageView;
            public GridViewHolder(@io.reactivex.annotations.NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}
