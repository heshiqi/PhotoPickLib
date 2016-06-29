package com.hsq.common.photopick.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by heshiqi on 16/5/8.
 */
public class ToastUtil {

	public static final void show(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
