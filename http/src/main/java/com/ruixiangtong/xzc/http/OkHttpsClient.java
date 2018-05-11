package com.ruixiangtong.xzc.http;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import com.ruixiangtong.common.utils.LogUtil;
import com.ruixiangtong.demo.http.R;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import retrofit.client.OkClient;

/**
 * <p>Summary:融入单项认证的OkHttpClient</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.net</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/14</p>
 * <p>Time:19:57</p>
 */
public class OkHttpsClient extends OkHttpClient {
    /**
     * 默认的证书密码
     */
    private static final String DEFAULT_PASSWORD = "111111";

    public static final int TIME_OUT_UNIT = 30 * 1000;

//    private Context context;
    /**
     * 证书密码
     */
    private String password;

    /**
     * 是否验证服务器主机地址
     */
    private boolean isVerifyHostName = false;

    /**
     * {@link OkHttpsClient}
     */
    public OkHttpsClient() {
        this(DEFAULT_PASSWORD);
    }

    /**
     * {@link OkHttpsClient}
     *
     * @param password 证书密码
     */
    public OkHttpsClient(String password) {
        this.password = password;
    }

    /**
     * okhttpsCliet单例
     */
    private static OkHttpsClient okHttpsClient;

    /**
     * 获取{@link #okHttpsClient}
     *
     * @return 获取到的单例
     */
    public static OkHttpsClient getOkHttpsClient(Context context) {
        if (okHttpsClient == null) {
            okHttpsClient = new OkHttpsClient();
            okHttpsClient.setConnectTimeout(TIME_OUT_UNIT, TimeUnit.MILLISECONDS);
            okHttpsClient.setReadTimeout(TIME_OUT_UNIT,TimeUnit.MILLISECONDS);
            okHttpsClient.setWriteTimeout(TIME_OUT_UNIT,TimeUnit.MILLISECONDS);
            okHttpsClient.build(context);
        }
        return okHttpsClient;
    }

    /**
     * 获取与retrofit融合的OkClient单例
     *
     * @return 获取到的单例
     */
    public static OkClient getOkClient(Context context) {
        return new OkClient(getOkHttpsClient(context));
    }

    public OkHttpsClient build(Context context) {
        SSLContext sslContext = getSSLContext(
                readKeyStore(R.raw.truststore, context));
        if (sslContext == null) {
            return null;
        }
        this.setSslSocketFactory(sslContext.getSocketFactory());
        if (!isVerifyHostName) {
            this.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }
        return this;
    }

    /**
     * 读取本地证书
     *
     * @param res 证书资源ID
     * @return 获取到的证书
     */
    @Nullable
    private KeyStore readKeyStore(@RawRes int res,Context context) {
        InputStream inputStream = null;
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = context.getResources().openRawResource(res);
            keyStore.load(inputStream, password.toCharArray());
        } catch (KeyStoreException kse) {
            LogUtil.getUtils().i(kse.getMessage());
        } catch (CertificateException ce) {
            LogUtil.getUtils().i(ce.getMessage());
        } catch (NoSuchAlgorithmException ne) {
            LogUtil.getUtils().i(ne.getMessage());
        } catch (IOException ie) {
            LogUtil.getUtils().i(ie.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ie) {
                    LogUtil.getUtils().i(ie.getMessage());
                }
            }
            return keyStore;
        }
    }

    /**
     * 获取SSL连接上下文
     *
     * @param keyStore
     * @return
     */
    @Nullable
    private SSLContext getSSLContext(@Nullable KeyStore keyStore) {
        if (keyStore == null) {
            return null;
        }
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());

            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (NoSuchAlgorithmException ne) {
            LogUtil.getUtils().i(ne.getMessage());
        } catch (KeyStoreException ke) {
            LogUtil.getUtils().i(ke.getMessage());
        } catch (UnrecoverableKeyException ue) {
            LogUtil.getUtils().i(ue.getMessage());
        } catch (KeyManagementException ke) {
            LogUtil.getUtils().i(ke.getMessage());
        } finally {
            return sslContext;
        }
    }
}
