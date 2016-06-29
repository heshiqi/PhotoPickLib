package com.hsq.common.photopick.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.widget.PhotoBrowseItemLayout;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by hsq on 2016/6/22.
 */
public class PhotoBrowseAdapter  extends PagerAdapter {

    private List<Photo> mPhotos;
    public PhotoBrowseAdapter(List<Photo> photos){
        mPhotos=photos;
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        PhotoBrowseItemLayout view = new PhotoBrowseItemLayout(container.getContext(), mPhotos.get(position));
        view.setPosition(position);
        PhotoView photoView = view.getImageView();
        String path="file://"+mPhotos.get(position).getPhotoPath();
        photoView.setImageUri(path);
        // Now just add PhotoView to ViewPager and return it
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
