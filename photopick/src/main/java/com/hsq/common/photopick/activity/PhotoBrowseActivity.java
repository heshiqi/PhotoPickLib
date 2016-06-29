/**
 * ****************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *****************************************************************************
 */
package com.hsq.common.photopick.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hsq.common.BaseActivity;
import com.hsq.common.photopick.R;
import com.hsq.common.photopick.adapter.PhotoBrowseAdapter;
import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.core.presenter.PhotoBrowsePresenter;
import com.hsq.common.photopick.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsq on 2016/6/22.
 */
public class PhotoBrowseActivity extends BaseActivity<PhotoBrowsePresenter> implements View.OnClickListener{

    public static final String PHOTO_LIST="PHOTO_LIST";
    public static final String POSITION="POSITION";
    private static final String ISLOCKED_ARG = "isLocked";

    private ViewPager mViewPager;
    private int position;
    private List<Photo> mPhotos=new ArrayList<>();
    private ImageView mBackBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        mBackBtn = (ImageView) findViewById(R.id.back);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initData() {
        mPhotos= (List<Photo>) getIntent().getSerializableExtra(PHOTO_LIST);
        mViewPager.setAdapter(new PhotoBrowseAdapter(mPhotos));
        if (getIntent() != null) {
            position = getIntent().getIntExtra(POSITION, 0);
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    protected void initListener() {
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id== R.id.back){
            finish();
        }

    }



    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }

}
