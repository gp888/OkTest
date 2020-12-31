package com.gp.oktest.retrofit;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileuploadService {

    //Retrofit会判断@Body的参数类型，如果参数类型为okhttp3.RequestBody,则Retrofit不做包装处理，
    // 直接丢给okhttp3处理。而MultipartBody是继承RequestBody，因此Retrofit不会自动包装这个对象。

    //同理，Retrofit会判断@Part的参数类型，如果参数类型为okhttp3.MultipartBody.Part,
    // 则Retrofit会把RequestBody封装成MultipartBody，再把Part添加到MultipartBody。
    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("users/image")
    Call<BaseResponse<String>> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("users/image")
    Call<BaseResponse<String>> uploadFileWithRequestBody(@Body MultipartBody multipartBody);


    @Multipart
    @POST("/upload")
    Observable<String> upload(@Part("interactionFile") RequestBody firstBody, @Part MultipartBody.Part file);


    //RequestBody firstBody = RequestBody.create( MediaType.parse("multipart/form-data"), "test")
//    Bitmap b = xxxx;//获取一个bitmap
//    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
//    byte[] byteArray = stream.toByteArray();
//
//    RequestBody body = RequestBody.create(MediaType.parse("image/png"), byteArray);//content-type为image/png，其中byteArray中的数据对应图中(5)处
//    MultipartBody.Part file = MultipartBody.Part.createFormData("interactionFile", "test.png", body);//分别对应图中(3)、(4)
}
