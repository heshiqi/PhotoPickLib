package com.hsq.common.photopick.utils;

import android.text.TextUtils;

/**
 * Created by heshiqi on 16/5/8.
 */
public class StringUtil {

    public static boolean isNull(String str) {
        return TextUtils.isEmpty(str) || "null".equals(str);
    }
}
