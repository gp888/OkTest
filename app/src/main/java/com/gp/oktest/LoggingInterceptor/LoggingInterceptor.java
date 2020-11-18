package com.gp.oktest.LoggingInterceptor;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间
        String requestBody = null;
        try {
            Buffer bufferedSink = new Buffer();
            request.body().writeTo(bufferedSink);
            Charset charset = request.body().contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            requestBody = bufferedSink.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("LoggingInterceptor", String.format("\n发送请求 %s on %s%n%s", request.url(), chain.connection(), request.headers().toString() + requestBody));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
//            byte[] bytes = response.body().bytes();
//            if(request.url().toString().endsWith("/user/captcha")||request.url().toString().endsWith("/user/login")) {
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String string = responseBody.string();
//                String decrypt = AcosUtil.decrypt(string.getBytes());

        //%n是换行的格式字符串,只能用在print输出语句中, 而\n是回车字符, 可以用在所有的字符串中
        Log.d("LoggingInterceptor", String.format(Locale.CHINA, "\n接收响应: [%s] %n返回json:【%s】%n%.1fms%n%s",
                response.request().url(),
                string,
                (t2 - t1) / 1e6d, //1*10^6
                response.headers()));
//            }

        return response;
    }
}
