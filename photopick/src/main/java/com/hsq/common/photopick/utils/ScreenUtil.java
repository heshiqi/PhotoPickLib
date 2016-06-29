package com.hsq.common.photopick.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by hsq on 2016/5/19.
 */
public class ScreenUtil {

    private static int screenWidth;
    private static int screenHeight;

    /**
     * 获取当前设备屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
           if(screenWidth>0)
               return  screenWidth;
           initDisplaySize(context);
        return screenWidth;
    }

    /**
     * 获取当前设备屏幕的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        if(screenHeight>0)
            return  screenHeight;
        initDisplaySize(context);
        return screenHeight;
    }

    /**
     * 将dip转换为px
     * @param context
     * @param dipValue
     * @return
     */
    public static float getPixels(Context context,int dipValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public int getSmallestScreenDimension(Context context) {
        if(screenWidth<0||screenHeight<0)
            initDisplaySize(context);
        return Math.min(screenWidth, screenHeight);
    }

    public static void initDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        screenWidth = outSize.x;
        screenHeight = outSize.y;
    }



    private ScreenUtil(){}
}
