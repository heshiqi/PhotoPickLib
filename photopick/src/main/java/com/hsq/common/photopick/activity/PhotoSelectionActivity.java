package com.hsq.common.photopick.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsq.common.BaseActivity;
import com.hsq.common.photopick.R;
import com.hsq.common.photopick.adapter.BBaseAdapter;
import com.hsq.common.photopick.adapter.PhotoSelectionAdapter;
import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.core.data.PhotoBucket;
import com.hsq.common.photopick.core.presenter.PhotoSelectionPresenter;
import com.hsq.common.photopick.core.view.PhotoSelectionView;
import com.hsq.common.photopick.helper.PhotoSelectionHelper;
import com.hsq.common.photopick.utils.ImageUtils;
import com.hsq.common.photopick.utils.PremissionManager;
import com.hsq.common.photopick.utils.ToastUtil;
import com.hsq.common.photopick.widget.GridInsetDecoration;
import com.hsq.common.photopick.widget.PhotoBucketsDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoSelectionActivity extends BaseActivity<PhotoSelectionPresenter> implements PhotoSelectionHelper.OnPhotoSelectionListener, PhotoSelectionView, View.OnClickListener {


    public static final String MAX_SELECTION_SIZE = "max_size";
    public static final String SELECTION_PHOTOS = "selection_photos";
    public static final int RESPOND_SELECTION_PHOTOS_CODE = 0X123;// 选取图片成功后返回码
    public static final int REQUEST_SELECTION_PHOTOS_CODE = 0X122;// 请求选取图片码
    private ImageView mBackBtn;
    private TextView mCompleteBtn;
    private RecyclerView mRecyclerView;
    private PhotoSelectionAdapter mSelectionAdapter;
    private View mPhotoOperateView;
    private TextView mPhotoBucketName;
    private TextView mPhotoSelectedCount;
    private PhotoBucketsDialog mPhotoBucketsDialog;
    private int mMaxSelectionSize;

    public final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 111;

    @Override
    protected void onCreated() {
        mMaxSelectionSize = getIntent().getIntExtra(MAX_SELECTION_SIZE, PhotoSelectionHelper.DEFULT_MAX_SELECTION_SIZE);
        /**
         * 指定选取图片的数量 必须调用此方法设置 默认设置选取9张
         */
        PhotoSelectionHelper.getInstance().setMaxSelectionSize(mMaxSelectionSize);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PhotoSelectionPresenter(this, this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo_selection;
    }

    @Override
    protected void initView() {

        mBackBtn = (ImageView) findViewById(R.id.back);
        mCompleteBtn = (TextView) findViewById(R.id.complete);
        mCompleteBtn.setText(String.format(getString(R.string.photo_complete), 0, mMaxSelectionSize));
        mCompleteBtn.setEnabled(false);

        mRecyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        mSelectionAdapter = new PhotoSelectionAdapter(this, null);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new
                GridInsetDecoration(this));
        mRecyclerView.setAdapter(mSelectionAdapter);
        mPhotoOperateView=findViewById(R.id.lt_photos_operate);
        mPhotoBucketName=(TextView)findViewById(R.id.tv_photo_bucket_name);
        mPhotoSelectedCount=(TextView)findViewById(R.id.tv_photo_browse);

    }

    @Override
    protected void initData() {
        List<String> premissions = new ArrayList<>(2);
        premissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        premissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> requests = PremissionManager.checkRequestPremissions(this, premissions);
        if (requests.isEmpty()) {
            mPresenter.onStart();
        } else {
            requestPermissions(requests.toArray(new String[requests.size()]), PremissionManager.REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
        }
    }



    @Override
    protected void initListener() {
        mPhotoSelectedCount.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mPhotoBucketName.setOnClickListener(this);
        mCompleteBtn.setOnClickListener(this);
        PhotoSelectionHelper.getInstance().setOnPhotoSelectionChangedListener(this);
        mSelectionAdapter.setOnItemClickListener(new BBaseAdapter.OnItemClickListener<Photo>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Photo data, int position) {
                mPresenter.onItemClicked(data,position,viewHolder.itemView);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PremissionManager.REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                boolean requestSuccess = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        requestSuccess = false;
                    }
                }
                if (requestSuccess) {
                    mPresenter.onStart();
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void initPhotoPhotoBuckets(final List<PhotoBucket> photoBuckets) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PhotoBucket photoBucket=photoBuckets.get(0);
                setPhotoDatas(photoBucket);
            }
        });
    }

    private void setPhotoDatas(PhotoBucket bucket){
        mSelectionAdapter.setDatas(bucket.getPhotos());
        mSelectionAdapter.notifyDataSetChanged();
        mPhotoBucketName.setText(bucket.getmBucketName());
        mPresenter.chageSelected(bucket);
    }

    private void showPhotoBucketsList() {
        if (mPhotoBucketsDialog == null) {
            mPhotoBucketsDialog = new PhotoBucketsDialog(this, R.style.DialogSlideAnim, mPresenter);
            mPhotoBucketsDialog.setOnItemClickListener(new BBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {
                    setPhotoDatas((PhotoBucket) data);
                    mPhotoBucketsDialog.dismiss();
                }
            });
        }
        mPhotoBucketsDialog.show();
    }

    @Override
    public void onClick(View v) {
         int id=v.getId();
        if(id==R.id.back){
            finish();
        }else if(id==R.id.complete){
            ArrayList<Photo> photos = new ArrayList<Photo>(PhotoSelectionHelper.getInstance().getSelected());
            Intent intent = new Intent();
            intent.putExtra(SELECTION_PHOTOS,photos);
            setResult(RESPOND_SELECTION_PHOTOS_CODE, intent);
            finish();
        }else if(id==R.id.tv_photo_bucket_name){
            showPhotoBucketsList();
        }else if(id== R.id.tv_photo_browse){
            mPresenter.onPreviewSelectedPhoto();
        }

    }

    @Override
    public void onDestroy() {
        PhotoSelectionHelper.getInstance().clear();
        super.onDestroy();
    }

    @Override
    public void onPhotoSelectionChanged(int count) {
        mCompleteBtn.setText(String.format(getString(R.string.photo_complete), count, mMaxSelectionSize));
        if(count==0){
            mCompleteBtn.setEnabled(false);
            mPhotoSelectedCount.setVisibility(View.GONE);
        }else{
            mCompleteBtn.setEnabled(true);
            mPhotoSelectedCount.setVisibility(View.VISIBLE);
            mPhotoSelectedCount.setText(getString(R.string.photo_browse, PhotoSelectionHelper.getInstance().getSelectedCount()));
        }
    }

    @Override
    public void onSelectedMaxCountComplete(int maxSize) {
        ToastUtil.show(this,getString(R.string.choose_max_photo,maxSize));
    }



    @Override
    public void launchTakePhotoActivity(Intent intent, int requestCode) {
        startActivityForResult(intent,requestCode);
    }

    @Override
    public void launchPhotoBrowseActivity(ArrayList<Photo> photos, int position, View view, int requestCode) {
        Bundle b = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN&&view!=null) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeThumbnailScaleUpAnimation(view,
                            ImageUtils.drawViewOntoBitmap(view), 0, 0);
            b = options.toBundle();
        }
        Intent intent=new Intent(this, PhotoBrowseActivity.class);
        intent.putExtra(PhotoBrowseActivity.PHOTO_LIST,photos);
        intent.putExtra(PhotoBrowseActivity.POSITION,position);
        mSelectionAdapter.notifyDataSetChanged();
        ActivityCompat.startActivityForResult(this,intent,requestCode,b);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode,resultCode,data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notifyDataSetChanged() {
        mSelectionAdapter.notifyDataSetChanged();
    }
}
