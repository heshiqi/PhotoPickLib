package com.hsq.common.photopick.core.view;

import android.content.Intent;
import android.view.View;

import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.core.data.PhotoBucket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public interface PhotoSelectionView extends IView {

    /**
     * 初始化相册数据
     * @param photoBuckets
     */
    void initPhotoPhotoBuckets(List<PhotoBucket> photoBuckets);

    /**
     * 打开拍照界面
     * @param intent
     */
    void launchTakePhotoActivity(Intent intent, int requestCode);

    /**
     * 打开照片浏览界面
     * @param photos
     */
    void launchPhotoBrowseActivity(ArrayList<Photo> photos, int position, View view, int requestCode);

    void notifyDataSetChanged();
}
