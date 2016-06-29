package com.hsq.common.photopick.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.ExifInterface;
import android.view.View;

import java.io.IOException;

/**
 * Created by hsq on 2016/6/23.
 */
public class ImageUtils {

    /**
     *  获取照片的旋转角度
     * @param photoPath
     * @return
     */
    public static int getRawBitmapRotate(String photoPath) {
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(photoPath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    rotate = 0;
                    break;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap drawViewOntoBitmap(View view) {
        Bitmap image = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);
        view.draw(canvas);
        return image;
    }
}
