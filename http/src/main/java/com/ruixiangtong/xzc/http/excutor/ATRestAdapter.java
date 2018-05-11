package com.ruixiangtong.xzc.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.ruixiangtong.xzc.http.OkHttpsClient;
import com.ruixiangtong.xzc.http.error.ATErrorHandler;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * <p>Summary:网络代理适配器</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.net</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/16</p>
 * <p>Time:13:57</p>
 */
public class ATRestAdapter {

    private static final String HEADER_TICKET = "ticket";

    /**
     * 获取RestAdapter.Builder
     *
     * @param endpoint    主机地址
     * @param handler     错误处理对象（可以为空，如果为空调用默认的错误处理）
     * @param isAddTicket 是否携带Ticket
     * @param ticket      如果需要携带Ticket，为Ticket的值
     * @return RestAdapter.Builder 获取到的网络代理
     */
    private static RestAdapter.Builder getBuilder(@NonNull String endpoint, @Nullable ErrorHandler handler,
                                                 boolean isAddTicket,
                                                 final String ticket, Context context) {
        Gson gson = new Gson();

        ATTypeConverter coverter = new ATTypeConverter(gson);

        ErrorHandler imp;
        if (handler == null)
            imp = new ATErrorHandler(gson);
        else
            imp = handler;

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .setConverter(coverter)
                .setErrorHandler(imp)
                .setClient(OkHttpsClient.getOkClient(context));
        if (isAddTicket) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader(HEADER_TICKET, ticket);
                }
            });
        }
        return builder;
    }

    /**
     * 获取登录业务网络代理
     *
     * @param endPoint 主机地址
     * @return 获取到的网络代理
     */
    public static RestAdapter.Builder obtainLoginBuilder(String endPoint) {
//        return getBuilder(endPoint, new LoginErrorHandler(new Gson()), false, null);
        return null;
    }

    /**
     * 获取AT+非登陆业务网络代理（携带Ticket）
     *
     * @param endPoint 主机地址
     * @param ticket   Ticket
     * @return 获取到的网络代理
     */
    public static RestAdapter.Builder obtainAtBuilder(String endPoint, String ticket, Context context) {
        return getBuilder(endPoint, new ATErrorHandler(new Gson()), true, ticket, context);
    }

    /**
     * 获取AT+非登录业务的网络代理（不携带Ticket）
     *
     * @param endPoint 主机地址
     * @return 获取到的网络代理
     */
    public static RestAdapter.Builder obtainAtBuilderWithoutTicket(String endPoint, Context context) {
        return getBuilder(endPoint, new ATErrorHandler(new Gson()), false, null, context);
    }
}
