package com.gp.oktest.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gp.oktest.BuildConfig;
import com.gp.oktest.LoggingInterceptor.LoggingInterceptor;
import com.gp.oktest.convert.DecodeConverterFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static Retrofit retrofit;

    public synchronized static Retrofit buildRetrofit() {
        if (retrofit == null) {
            LoggingInterceptor logging = new LoggingInterceptor();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("NetURL.BASE_URL")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            if(BuildConfig.DEBUG) {
                builder = builder.addConverterFactory(DecodeConverterFactory.create());
            }else{
                builder = builder.addConverterFactory(gsonConverterFactory);
            }
            retrofit = builder.build();
        }
        return retrofit;
    }

    /**
     * 把File对象转化成MultipartBody:
     * @param files
     * @return
     */
    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    /**
     * 把File转化成MultipartBody.Part:
     * @param files
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
