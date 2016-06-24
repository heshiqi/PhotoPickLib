package com.android.common.core.interactor;

import android.content.Context;

import com.android.common.core.data.Photo;
import com.android.common.core.data.PhotoBucket;
import com.android.common.core.data.datasource.PhotoDataSouce;

import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoDataInteractor {

    private PhotoDataSouce mPhotoDataSouce;

    public PhotoDataInteractor(){
        mPhotoDataSouce=new PhotoDataSouce();
    }



    public static interface MediaStoreBucketsResultListener {

        public void onBucketsLoaded(List<PhotoBucket> buckets);
    }


    public void getPhotoBuckets(final Context context, final MediaStoreBucketsResultListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<PhotoBucket>bucketList=mPhotoDataSouce.getPhotoBuckets(context);
                if(bucketList!=null&&!bucketList.isEmpty())
                    bucketList.get(0).getPhotos().add(0,new Photo(-1,"",Photo.TYPE_CAMERA));//添加相机
                if(listener!=null)
                    listener.onBucketsLoaded(bucketList);

            }
        }){}.start();

    }
}
