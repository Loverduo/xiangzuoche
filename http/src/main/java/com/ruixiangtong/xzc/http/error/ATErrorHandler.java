package com.ruixiangtong.xzc.http.error;

/**
 * <p>Summary:</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.domain.comm</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/20</p>
 * <p>Time:11:55</p>
 */

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruixiangtong.common.utils.LogUtil;
import com.ruixiangtong.xzc.http.bean.RespError;

import java.io.InputStreamReader;

import javax.net.ssl.SSLHandshakeException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * 处理Http响应错误
 */
public class ATErrorHandler implements ErrorHandler {

    public static final int HTTP_RESP_ERROR_CODE_400 = 400;
    public static final int HTTP_RESP_ERROR_CODE_401 = 401;
    public static final int HTTP_RESP_ERROR_CODE_500 = 500;
    public static final int HTTP_RESP_ERROR_CODE_404 = 404;

    public static final String UNKNOWN_ERROR_MESSAGE = "未知错误";
    /**
     * 未知错误
     */
    public static final int UNKNOWN_ERROR_CODE = -0x01;
    /**
     * 客户端错误
     */
    public static final int CLIENT_ERROR_CODE = -0x02;

    public static final String REQUEST_PARAMS_NOT_VALID_MESSAGE = "请求参数非法";
    /**
     * 请求参数非法
     */
    public static final String REQUEST_PARAMS_NOT_VALID = "request_params_not_valid";
    /**
     * 请求参数非法Code
     */
    public static final int REQUEST_PARAMS_NOT_VALID_CODE = -0x10;


    public static final String REQUEST_PARAMS_ERROR_MESSAGE = "请求参数格式错误";
    /**
     * 请求参数格式错误
     */
    public static final String REQUEST_PARAMS_ERROR = "request_params_error";
    /**
     * 请求参数格式错误Code
     */
    public static final int REQUEST_PARAMS_ERROR_CODE = -0x11;


    public static final String NOT_AUTHORIZED_MESSAGE = "ticket验证未通过";
    /**
     * ticket验证未通过
     */
    public static final String NOT_AUTHORIZED = "not_authorized";
    /**
     * ticket验证未通过Code
     */
    public static final int NOT_AUTHORIZED_CODE = -0x12;

    /**
     * 账户不存在
     */
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "帐户不存在";
    /**
     * 账户不存在
     */
    public static final String ACCOUNT_NOT_EXIST_ERROR = "account_is_not_exist";
    /**
     * 账户不存在异常Code
     */
    public static final int ACCOUNT_NOT_EXIST_ERROR_CODE = -0x16;

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "服务器内部异常";
    /**
     * 服务器内部异常
     */
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    /**
     * 服务器内部异常Code
     */
    public static final int INTERNAL_SERVER_ERROR_CODE = -0x13;

    public static final String EXCEPTION_HANDLE_ERROR_MESSAGE = "异常处理错误";
    /**
     * 异常处理错误
     */
    public static final String EXCEPTION_HANDLE_ERROR = "exception_handle_error";
    /**
     * 异常处理错误Code
     */
    public static final int EXCEPTION_HANDLE_ERROR_CODE = -0x14;

    /**
     * 网络异常code
     */
    public static final int EXCEPTION_NET_UNREACHABLE_CODE = -0x15;
    public static final String EXCEPTION_NET_UNREACHABLE_MESSAGE = "网络异常，请检查网络设置";

    /**
     * 本地时间戳有误
     */
    public static final String REQUEST_TIMEOUT_MESSAGE = "请求时间戳超时";
    public static final String REQUEST_TIMEOUT = "request_timeout";
    public static final int REQUEST_TIMEOUT_CODE = -0x27;

    /**
     * 本地时间有误
     */
//    public static final String REQUEST_TIMEERROR_MESSAGE = "系统时间与北京时间不一致，当前操作存在风险，请您设置后重试！";
    public static final String REQUEST_TIMEERROR_MESSAGE = "服务器繁忙，请重试";
    public static final int REQUEST_TIMEERROR_CODE = -0x28;

    @IntDef(value = {REQUEST_PARAMS_NOT_VALID_CODE, REQUEST_PARAMS_ERROR_CODE,
            NOT_AUTHORIZED_CODE, INTERNAL_SERVER_ERROR_CODE, EXCEPTION_HANDLE_ERROR_CODE,
            UNKNOWN_ERROR_CODE, CLIENT_ERROR_CODE,EXCEPTION_NET_UNREACHABLE_CODE,REQUEST_TIMEOUT_CODE,
            REQUEST_TIMEERROR_CODE,ACCOUNT_NOT_EXIST_ERROR_CODE})
    public static @interface HTTP_RESP_ERROR_CODE {
    }

    private Gson gson;

    public ATErrorHandler(Gson gson) {
        this.gson = gson;
    }

    public Throwable handleError(RetrofitError cause) {
        if (cause == null || cause.getResponse() == null){
            if (cause.getCause() instanceof SSLHandshakeException){
                return new AtHttpResponseException(REQUEST_TIMEERROR_CODE,
                        REQUEST_TIMEERROR_MESSAGE);
            }
//            if (!StateParams.getStateParams().isNetWorkOpen()){
//                return new AtHttpResponseException(EXCEPTION_NET_UNREACHABLE_CODE,
//                        EXCEPTION_NET_UNREACHABLE_MESSAGE);
//            }
            return cause;
        }
        Response response = cause.getResponse();
        try {
            InputStreamReader isr = new InputStreamReader(response.getBody().in(), "UTF-8");
            RespError error = gson.fromJson(isr, RespError.class);
            if (error != null && !TextUtils.isEmpty(error.getErrCode())) {
                String errorCode = error.getErrCode();
                switch (response.getStatus()) {
                    case HTTP_RESP_ERROR_CODE_400:
                        if (errorCode.equals(REQUEST_PARAMS_NOT_VALID))
                            return new AtHttpResponseException(REQUEST_PARAMS_NOT_VALID_CODE,
                                    REQUEST_PARAMS_NOT_VALID_MESSAGE);
                        else if (errorCode.equals(REQUEST_PARAMS_ERROR))
                            return new AtHttpResponseException(REQUEST_PARAMS_ERROR_CODE,
                                    REQUEST_PARAMS_ERROR_MESSAGE);
                        else
                            return new AtHttpResponseException(UNKNOWN_ERROR_CODE, error.getErrCode());
                    case HTTP_RESP_ERROR_CODE_401:
                        if (errorCode.equals(NOT_AUTHORIZED))
                            return new AtHttpResponseException(NOT_AUTHORIZED_CODE,
                                    NOT_AUTHORIZED_MESSAGE);
                        else
                            return new AtHttpResponseException(UNKNOWN_ERROR_CODE,  error.getErrCode());
                    case HTTP_RESP_ERROR_CODE_500:
                        if (errorCode.equals(INTERNAL_SERVER_ERROR))
                            return new AtHttpResponseException(INTERNAL_SERVER_ERROR_CODE,
                                    INTERNAL_SERVER_ERROR_MESSAGE);
                        else if (errorCode.equals(EXCEPTION_HANDLE_ERROR))
                            return new AtHttpResponseException(EXCEPTION_HANDLE_ERROR_CODE,
                                    EXCEPTION_HANDLE_ERROR_MESSAGE);
                        else
                            return new AtHttpResponseException(UNKNOWN_ERROR_CODE,  error.getErrCode());
                    case HTTP_RESP_ERROR_CODE_404:
                        if (errorCode.equals(ACCOUNT_NOT_EXIST_ERROR))
                            return new AtHttpResponseException(ACCOUNT_NOT_EXIST_ERROR_CODE,
                                    ACCOUNT_NOT_EXIST_MESSAGE);
                        return new AtHttpResponseException(UNKNOWN_ERROR_CODE,  error.getErrCode());
                    case REQUEST_TIMEOUT_CODE:
                        return new AtHttpResponseException(REQUEST_TIMEOUT_CODE,REQUEST_TIMEOUT_MESSAGE);
                    default:
                        return new AtHttpResponseException(UNKNOWN_ERROR_CODE,  error.getErrCode());
                }
            }
        } catch (Exception e){
            LogUtil.getUtils().i(e.getMessage());
//            return new AtHttpResponseException(CLIENT_ERROR_CODE, e.getMessage());
            return new AtHttpResponseException(CLIENT_ERROR_CODE, "服务器繁忙，请重试");
        }
        return cause;
    }

    public static class AtHttpResponseException extends Exception {

        private
        @HTTP_RESP_ERROR_CODE
        int errorCode;

        @HTTP_RESP_ERROR_CODE
        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(@HTTP_RESP_ERROR_CODE int errorCode) {
            this.errorCode = errorCode;
        }

        public AtHttpResponseException() {
            super();
        }

        public AtHttpResponseException(String message) {
            super(message);
        }

        public AtHttpResponseException(@HTTP_RESP_ERROR_CODE int errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
        }

        public AtHttpResponseException(String message, Throwable throwable) {
            super(message, throwable);
        }

        public AtHttpResponseException(Throwable throwable) {
            super(throwable);
        }
    }
}
