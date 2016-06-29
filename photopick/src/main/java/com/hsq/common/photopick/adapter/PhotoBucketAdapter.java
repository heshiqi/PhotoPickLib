package com.hsq.common.photopick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hsq.common.photopick.R;
import com.hsq.common.photopick.core.data.PhotoBucket;
import com.hsq.common.photopick.widget.CustomImageView;

import java.util.List;

/**
 * Created by hsq on 2016/5/21.
 */
public class PhotoBucketAdapter extends BBaseAdapter<PhotoBucket,PhotoBucketAdapter.BucketViewHolder> {


    public PhotoBucketAdapter(Context context, List<PhotoBucket> datas) {
        super(context, datas);
    }


    public static class BucketViewHolder extends RecyclerView.ViewHolder{
        public CustomImageView imageView;
        public TextView name;
        public View checkedView;
        public BucketViewHolder(View itemView) {
            super(itemView);
            imageView=(CustomImageView)itemView.findViewById(R.id.iv_bucket_thumbnail);
            name=(TextView)itemView.findViewById(R.id.tv_bucket_name);
            checkedView=itemView.findViewById(R.id.checked_view);
        }

        public void render(PhotoBucket bucket){
            imageView.setImageUrl("file://"+bucket.getmCovePath(),R.mipmap.ic_photo_bg);
            name.setText(bucket.getmBucketName());
            checkedView.setVisibility(bucket.isChecked()?View.VISIBLE:View.GONE);

        }
    }
    @Override
    public BucketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mInflater.inflate(R.layout.item_photo_bucket,parent,false);
        return new BucketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BucketViewHolder holder, final int position) {
        final PhotoBucket bucket=getItemData(position);
        holder.render(bucket);
        if(mItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(holder,bucket,position);
                }
            });
        }
    }


}
