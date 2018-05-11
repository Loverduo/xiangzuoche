package com.ruixiangtong.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xurj on 2016/7/23.
 */
public class RXTToast {
    public static void showShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
