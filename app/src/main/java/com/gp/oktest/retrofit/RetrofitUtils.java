package com.gp.oktest.retrofit;

import retrofit2.Retrofit;


public class RetrofitUtils {


    /**
     * 获取Retrofit对象
     * @baseUrl 基础url
     * @return Retrofit 对象
     */
    protected static Retrofit getRetrofit(String baseUrl) {

        Retrofit tempRetrofit = null;

//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(OkHttpHelper.getInstance());
//
//        if(BuildConfig.APP_DEBUG_MODE) {
//            builder = builder.addConverterFactory(DecodeConverterFactory.create());
//        }else{
//            builder = builder.addConverterFactory(GsonConverterFactory.create());
//        }

//        tempRetrofit = builder.build();

        return tempRetrofit;
    }


}
