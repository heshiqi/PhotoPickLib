package com.hsq.common.photopick.core.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.core.data.PhotoBucket;
import com.hsq.common.photopick.core.interactor.PhotoDataInteractor;
import com.hsq.common.photopick.core.view.PhotoSelectionView;
import com.hsq.common.photopick.helper.PhotoSelectionHelper;
import com.hsq.common.photopick.utils.FileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoSelectionPresenter implements IPresenter {

    static final int REQUEST_CAMERA=1;
    static final int REQUEST_BROWSE=2;
    private PhotoSelectionView mView;

    private PhotoDataInteractor mInteractor;

    private final WeakReference<Context> mContext;

    private List<PhotoBucket> mPhotoBuckets;

    private File mPhotoFile;

    private int mCurrentBuckets=-1;//记录当前选中的相册

    public PhotoSelectionPresenter(Context context, PhotoSelectionView view) {
        mView = view;
        mInteractor = new PhotoDataInteractor();
        mContext = new WeakReference<Context>(context);
    }

    @Override
    public void onStart() {
        loadPhotoBuckets();
    }

    /**
     * 加载手机图片
     */
    public void loadPhotoBuckets() {
        mInteractor.getPhotoBuckets(mContext.get(), new PhotoDataInteractor.MediaStoreBucketsResultListener() {
            @Override
            public void onBucketsLoaded(List<PhotoBucket> buckets) {
                if(mView==null)
                    return;
               if(buckets!=null&&!buckets.isEmpty()){
                   mPhotoBuckets=new ArrayList<PhotoBucket>(buckets);

                           mView.initPhotoPhotoBuckets(mPhotoBuckets);



               }
            }
        });
    }

    public List<PhotoBucket> getPhotoBuckets(){
        return mPhotoBuckets;
    }

    public void chageSelected(PhotoBucket bucket){
        if(mPhotoBuckets==null)
            return;
        for (int i=0;i<mPhotoBuckets.size();i++){
            PhotoBucket photoBucket=mPhotoBuckets.get(i);
            if(photoBucket.equals(bucket)){
                photoBucket.setChecked(true);
                mCurrentBuckets=i;
            }else{
                photoBucket.setChecked(false);
            }
        }
    }

    public void onItemClicked(Photo photo, int postion, View view){
        int type=photo.getType();
        if(Photo.TYPE_CAMERA==type){//拍照
              takePhoto();
        }else{//查看大图
            if(mCurrentBuckets>=0&&mCurrentBuckets<mPhotoBuckets.size()){
                 List<Photo> photos=new ArrayList<>(mPhotoBuckets.get(mCurrentBuckets).getPhotos());
                if(mCurrentBuckets==0){
                    photos.remove(0);
                    postion-=1;
                }
                mView.launchPhotoBrowseActivity((ArrayList<Photo>) photos,postion,view,REQUEST_BROWSE);
            }
        }
    }

    /**
     * 预览选中的照片
     */
    public void onPreviewSelectedPhoto(){
        List<Photo> selectedPhtos= new ArrayList<>(PhotoSelectionHelper.getInstance().getSelected());
        mView.launchPhotoBrowseActivity((ArrayList<Photo>) selectedPhtos,0,null,REQUEST_BROWSE);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        if (null == mPhotoFile) {
            Intent takePictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            mPhotoFile = FileUtils.getCameraPhotoFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(mPhotoFile));
            mView.launchTakePhotoActivity(takePictureIntent,REQUEST_CAMERA);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case REQUEST_CAMERA://拍照返回
                if (null != mPhotoFile) {
                    if (resultCode == Activity.RESULT_OK) {
                        FileUtils.scanMediaJpegFile(mContext.get(), mPhotoFile, new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri) {
                               if(mView!=null){
                                 loadPhotoBuckets();
                               }
                            }
                        });
                    } else {
                        mPhotoFile.delete();
                    }
                    mPhotoFile = null;
                }
                return;
           case REQUEST_BROWSE:
               mView.notifyDataSetChanged();
               break;
        }
    }

    @Override
    public void onDestroy() {
        mView=null;
    }
}
