package com.hsq.common.photopick.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;

import java.io.File;

/**
 * Created by hsq on 2016/5/22.
 */
public class FileUtils {

    public final static String PROJECT_NAME="common";
    /**
     * 扫描指定的路径将相片添加到本地媒体库，不执行此操作新拍照的相片不会自动添加到媒体库
     *
     * @param context
     * @param file
     * @param listener
     */
    public static void scanMediaJpegFile(final Context context,
                                         final File file, final MediaScannerConnection.OnScanCompletedListener listener) {
        MediaScannerConnection.scanFile(context,
                new String[] { file.getAbsolutePath() },
                new String[] { "image/jpg" }, listener);
    }

    /**
     * 创建拍照相片的保存路径
     *
     * @return
     */
    public static File getCameraPhotoFile() {
        File dir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(dir, "photopick_" + System.currentTimeMillis() + ".jpg");
    }
}
