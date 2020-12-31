package com.gp.oktest.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxjavaUtil {

    public static Disposable loop(long period, final OnRxLoopListener listener){
        return Observable.interval(period, TimeUnit.MILLISECONDS)
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return listener.takeWhile();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long l) {
                        listener.onExecute();
                    }
                    @Override
                    public void onComplete() {
                        listener.onFinish();
                    }
                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }
                });
    }


    public interface OnRxLoopListener{
        //是否循环
        Boolean takeWhile() throws Exception;
        //执行事件, 在主线程回调
        void onExecute();
        //循环结束
        void onFinish();
        //事件执行失败, 在主线程回调
        void onError(Throwable e);
    }
}
