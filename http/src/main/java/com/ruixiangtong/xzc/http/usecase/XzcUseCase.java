package com.ruixiangtong.xzc.http.usecase;

import android.text.TextUtils;

import com.ruixiangtong.common.utils.LogUtil;
import com.ruixiangtong.common.utils.RXTToast;
import com.ruixiangtong.xzc.http.HttpApplication;
import com.ruixiangtong.xzc.http.error.ATErrorHandler;

import retrofit.RetrofitError;
import rx.Subscriber;

/**
 * <p>Summary:AT+业务处理基类</p>
 * <p>Description:</p>
 * <p>Package:com.ruixiangtong.xzc.http.usecase</p>
 * <p>Date:2016/7/23</p>
 * <p>Time:00:09</p>
 */
public abstract class XzcUseCase<T> extends UseCase<T> {
    public static abstract class XzcSubscriber<T> extends Subscriber<T> {
        @Override
        public void onError(Throwable e) {
            if (e instanceof ATErrorHandler.AtHttpResponseException) {
                handleActomError(castException(e));
            } else if (e instanceof RetrofitError) {
                if (((RetrofitError) e).getKind() == RetrofitError.Kind.NETWORK) {
                    obtainErrorMsg_REQUEST_PARAMS_NETWORK_ERROR();
                }
            } else {
                LogUtil.getUtils().i("---UnknownException---");
                LogUtil.getUtils().i(e.getMessage());
                String e1 = obtainErrorMsg_UNKCATCHED_ERROR_CODE();
                if (TextUtils.isEmpty(e1)) {
                    //START ADD BY LYQ 2016-1-22 e.getMessage为空时，导致底层弹框崩溃，针对与此得特殊处理（未知异常）
                    if(TextUtils.isEmpty(e.getMessage())){
                        RXTToast.showShort(HttpApplication.context, ATErrorHandler.UNKNOWN_ERROR_MESSAGE);
                    }else {//END ADD BY LYQ 2016-1-22 e.getMessage为空时，导致底层弹框崩溃，针对与此得特殊处理（未知异常）

                        RXTToast.showShort(HttpApplication.context, e.getMessage());
                    }
                } else {
                    RXTToast.showShort(HttpApplication.context, e1);
                }
            }
        }

        /**
         * 处理安通+业务错误
         *
         * @param e
         */
        private void handleActomError(ATErrorHandler.AtHttpResponseException e) {
            switch (e.getErrorCode()) {
                case ATErrorHandler.REQUEST_TIMEERROR_CODE:
                    RXTToast.showShort(HttpApplication.context, e.getMessage());
                    break;
                case ATErrorHandler.REQUEST_PARAMS_NOT_VALID_CODE:
                    String errMsg1 = obtainErrorMsg_REQUEST_PARAMS_NOT_VALID_CODE();
                    if (TextUtils.isEmpty(errMsg1)) {
                        RXTToast.showShort(HttpApplication.context, e.getMessage());
                    } else {
                        RXTToast.showShort(HttpApplication.context, errMsg1);
                    }
                    break;
                case ATErrorHandler.REQUEST_PARAMS_ERROR_CODE:
                    String errMsg2 = obtainErrorMsg_REQUEST_PARAMS_ERROR_CODE();
                    if (TextUtils.isEmpty(errMsg2)) {
                        RXTToast.showShort(HttpApplication.context, e.getMessage());
                    } else {
                        RXTToast.showShort(HttpApplication.context, errMsg2);
                    }
                    break;
                case ATErrorHandler.NOT_AUTHORIZED_CODE:
                    break;
                case ATErrorHandler.INTERNAL_SERVER_ERROR_CODE:
                    String errMsg4 = obtainErrorMsg_INTERNAL_SERVER_ERROR_CODE();
                    if (TextUtils.isEmpty(errMsg4)) {
                        RXTToast.showShort(HttpApplication.context, e.getMessage());
                    } else {
                        RXTToast.showShort(HttpApplication.context, errMsg4);
                    }
                    break;
                case ATErrorHandler.EXCEPTION_HANDLE_ERROR_CODE:
                    String errMsg5 = obtainErrorMsg_EXCEPTION_HANDLE_ERROR_CODE();
                    if (TextUtils.isEmpty(errMsg5)) {
                        RXTToast.showShort(HttpApplication.context, e.getMessage());
                    } else {
                        RXTToast.showShort(HttpApplication.context, errMsg5);
                    }
                    break;
                case ATErrorHandler.ACCOUNT_NOT_EXIST_ERROR_CODE:
                    handleAccountNotExistMessage();
                    break;
                case ATErrorHandler.UNKNOWN_ERROR_CODE:
                    String errMsg6 = obtainErrorMsg_UNKNOWN_ERROR_CODE(e.getMessage());
                    if (TextUtils.isEmpty(errMsg6)) {
                        handleUNKnowErrorMessage(e.getMessage());
                    } else {
                        RXTToast.showShort(HttpApplication.context, errMsg6);
                    }
                    break;
                case ATErrorHandler.CLIENT_ERROR_CODE:
                    String errMsg7 = obtainErrorMsg_CLIENT_ERROR_CODE();
                    if (TextUtils.isEmpty(errMsg7)) {
                        RXTToast.showShort(HttpApplication.context, e.getMessage());
                    } else {
                        RXTToast.showShort(HttpApplication.context, errMsg7);
                    }
                    break;
                case ATErrorHandler.EXCEPTION_NET_UNREACHABLE_CODE:
                    String errmsg = ATErrorHandler.EXCEPTION_NET_UNREACHABLE_MESSAGE;
                    RXTToast.showShort(HttpApplication.context, errmsg);
                default:
                    break;
            }
        }

        /**
         * 处理错误信息的方式
         */
        protected void handleUNKnowErrorMessage(String message){
            RXTToast.showShort(HttpApplication.context, message);
        }

        protected void handleAccountNotExistMessage(){

        }

        /**
         * 用于子类处理请求参数非法的错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_REQUEST_PARAMS_NOT_VALID_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理网络状态错误的错误提示信息
         *
         * @return 错误提示信息
         */
        protected void obtainErrorMsg_REQUEST_PARAMS_NETWORK_ERROR() {
            RXTToast.showShort(HttpApplication.context, ATErrorHandler.EXCEPTION_NET_UNREACHABLE_MESSAGE);
        }

        /**
         * 用于子类处理请求参数格式错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_REQUEST_PARAMS_ERROR_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理ticket验证未通过的错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_NOT_AUTHORIZED_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理服务器内部异常的错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_INTERNAL_SERVER_ERROR_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理异常处理错误的错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_EXCEPTION_HANDLE_ERROR_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理未知错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_UNKNOWN_ERROR_CODE(String message) {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理客户端错误错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_CLIENT_ERROR_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理未被捕获到的错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainErrorMsg_UNKCATCHED_ERROR_CODE() {
            return obtainCommonErrorMsg();
        }

        /**
         * 用于子类处理通用的错误提示信息
         *
         * @return 错误信息
         */
        protected String obtainCommonErrorMsg() {
            return "";
        }

        /**
         * 异常转换
         *
         * @param throwable
         * @return
         */
        public static ATErrorHandler.AtHttpResponseException castException(Throwable throwable) {
            if (throwable instanceof ATErrorHandler.AtHttpResponseException) {
                return ((ATErrorHandler.AtHttpResponseException) throwable);
            }
            return null;
        }
    }

}
