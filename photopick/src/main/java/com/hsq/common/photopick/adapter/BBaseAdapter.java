package com.hsq.common.photopick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by hsq on 2016/5/21.
 */
public abstract class BBaseAdapter<D,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<D> mDatas;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public interface OnItemClickListener<D> {
        void onItemClick(RecyclerView.ViewHolder viewHolder, D data, int position);
    }
    protected OnItemClickListener<D> mItemClickListener;

    public BBaseAdapter(Context context,List<D> datas){
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
        this.mDatas=datas;
    }

    public void setDatas(List<D> datas){
        this.mDatas=datas;
    }

    public void setOnItemClickListener(OnItemClickListener<D> listener){
        this.mItemClickListener=listener;
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    public List<D> getDatas(){
        return mDatas;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public D getItemData(int position){
        return mDatas==null?null:mDatas.get(position);
    }
}
