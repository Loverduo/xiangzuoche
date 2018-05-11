package com.ruixiangtong.xzc.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Summary:通用HTTP响应错误信息bean</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.bean</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/20</p>
 * <p>Time:10:20</p>
 */
public class RespError implements Parcelable {
//    "hostId":"",
//            "requestId":"",
//            "errCode":"",
//            "message":""

    private String hostId;
    private String requestId;
    /**
     * 业务错误码
     */
    private String errCode;
    /**
     * 业务错误信息
     */
    private String message;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hostId);
        dest.writeString(this.requestId);
        dest.writeString(this.errCode);
        dest.writeString(this.message);
    }

    public RespError() {
    }

    protected RespError(Parcel in) {
        this.hostId = in.readString();
        this.requestId = in.readString();
        this.errCode = in.readString();
        this.message = in.readString();
    }

    public static final Creator<RespError> CREATOR = new Creator<RespError>() {
        public RespError createFromParcel(Parcel source) {
            return new RespError(source);
        }

        public RespError[] newArray(int size) {
            return new RespError[size];
        }
    };
}
