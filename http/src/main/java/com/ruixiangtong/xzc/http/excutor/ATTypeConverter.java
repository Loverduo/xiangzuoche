package com.ruixiangtong.xzc.http.excutor;

/**
 * <p>Summary:</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.domain.comm</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/20</p>
 * <p>Time:11:54</p>
 */

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * AT+数据通用类型转换器
 */
public class ATTypeConverter implements Converter {
    private Gson gson;
    private String charset = "UTF-8";

    public ATTypeConverter(Gson gson) {
        this.gson = gson;
    }

    public ATTypeConverter(Gson gson, String charset) {
        this.gson = gson;
        this.charset = charset;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String charset = this.charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(body.in(), charset);
            return gson.fromJson(isr, type);
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (JsonParseException e) {
            throw new ConversionException(e);
        } catch (ClassCastException cce) {
            throw new ConversionException(cce);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            return new JsonTypedOutput(gson.toJson(object).getBytes(charset), charset);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}
