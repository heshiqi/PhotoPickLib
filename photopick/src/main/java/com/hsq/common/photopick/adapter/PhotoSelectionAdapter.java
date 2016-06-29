package com.hsq.common.photopick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hsq.common.photopick.R;
import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.helper.PhotoSelectionHelper;
import com.hsq.common.photopick.utils.ScreenUtil;
import com.hsq.common.photopick.widget.PhotoItemLayout;

import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoSelectionAdapter extends BBaseAdapter<Photo,PhotoSelectionAdapter.BaseViewHolder> {

    public final static int ITEM_TYPE_CAMERA = 0;
    public final static int ITEM_TYPE_PHOTO = 1;
    private int mItemWidth;

    public PhotoSelectionAdapter(Context context,List<Photo> photos) {
        super(context,photos);
        int screenWidth = ScreenUtil.getScreenWidth(mContext);
        int gridViewSpace = context.getResources().getDimensionPixelSize(R.dimen.photo_gridview_space);
        mItemWidth = (screenWidth - gridViewSpace * 4) / 3;
        mItemWidth = screenWidth  / 3;
    }


    @Override
    public int getItemViewType(int position) {
        if (mDatas == null)
            return ITEM_TYPE_PHOTO;
        Photo photo = getItemData(position);

        if (Photo.TYPE_CAMERA == photo.getType())
            return ITEM_TYPE_CAMERA;

        if (Photo.TYPE_PHOTO == photo.getType())
            return ITEM_TYPE_PHOTO;
        return ITEM_TYPE_PHOTO;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_TYPE_CAMERA:
                viewHolder = new CamearViewHolder(mInflater.inflate(R.layout.item_grid_camera, parent, false));
                break;
            case ITEM_TYPE_PHOTO:
                viewHolder = new PhotoViewHolder(mInflater.inflate(R.layout.item_grid_photo, parent, false));
                break;
            default:
                viewHolder = new PhotoViewHolder(mInflater.inflate(R.layout.item_grid_photo, parent, false));
                break;

        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {

        final Photo photo=getItemData(position);
        holder.render(photo,mItemWidth);
        if(mItemClickListener!=null){
            final RecyclerView.ViewHolder viewHolder=holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(viewHolder,photo,position);
                }
            });
        }

    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        abstract void render(Photo photo, int itemWidth);
    }

    public static class PhotoViewHolder extends BaseViewHolder {

        public PhotoItemLayout photoItemLayout;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            photoItemLayout=(PhotoItemLayout)itemView.findViewById(R.id.lt_PhotoItemLayout);
        }

        @Override
        void render(Photo photo, int itemWidth) {
            photoItemLayout.getLayoutParams().width=itemWidth;
            photoItemLayout.getLayoutParams().height=itemWidth;
            photoItemLayout.getImageView().getLayoutParams().width=itemWidth;
            photoItemLayout.getImageView().getLayoutParams().height=itemWidth;
            photoItemLayout.getImageView().setImageUrl("file://"+photo.getPhotoPath(), R.color.photo_gallery_item_bg);
            photoItemLayout.setPhotoSelection(photo);
            photoItemLayout.setChecked( PhotoSelectionHelper.getInstance().isSelected(photo));
        }
    }

    public static class CamearViewHolder extends BaseViewHolder {
        public ImageView mCamearBtn;
        public CamearViewHolder(View itemView) {
            super(itemView);
            mCamearBtn= (ImageView) itemView.findViewById(R.id.iv_camera_button);
        }

        @Override
        void render(Photo photo, int itemWidth) {
            mCamearBtn.getLayoutParams().width=itemWidth;
            mCamearBtn.getLayoutParams().height=itemWidth;
        }
    }
}
