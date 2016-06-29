package com.hsq.common.photopick.helper;


import com.hsq.common.photopick.core.data.Photo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoSelectionHelper {

    public static final int DEFULT_MAX_SELECTION_SIZE = 9;
//    private Context mContext;
    private static PhotoSelectionHelper mPhotoSelectionHelper;


    private final ArrayList<Photo> mSelectedPhotoList;
    private OnPhotoSelectionListener onPhotoSelectionChanged;
    private int mMaxSelectionSize;

    public static interface OnPhotoSelectionListener {
        /**
         *   相片选取状态改变
         * @param count  所选择图片的数量
         */
        public void onPhotoSelectionChanged(int count);

        public void onSelectedMaxCountComplete(int maxSize);
    }

    public static PhotoSelectionHelper getInstance() {
        if (mPhotoSelectionHelper == null) {
            synchronized (PhotoSelectionHelper.class) {
                if (mPhotoSelectionHelper == null) {
                    mPhotoSelectionHelper = new PhotoSelectionHelper();
                }
            }
        }
        return mPhotoSelectionHelper;
    }

    private PhotoSelectionHelper() {
//        this.mContext = context;
        this.mMaxSelectionSize = DEFULT_MAX_SELECTION_SIZE;
        mSelectedPhotoList = new ArrayList<Photo>();
    }

    /**
     * 设置可以选取照片的最大数量
     * @param maxSelectionSize
     */
    public void setMaxSelectionSize(int maxSelectionSize) {
        this.mMaxSelectionSize = maxSelectionSize;
    }

    /**
     * 判断选择的照片数量是不是达到最大可选数量
     * @return
     */
    public boolean isCompleteSelection() {
        boolean result = getSelectedCount() == mMaxSelectionSize;
		if(result&&onPhotoSelectionChanged!=null)
            onPhotoSelectionChanged.onSelectedMaxCountComplete(mMaxSelectionSize);
        return result;
    }

    /**
     * 设置图片选取数量监听
     * @param onPhotoSelectionChanged
     */
    public void setOnPhotoSelectionChangedListener(OnPhotoSelectionListener onPhotoSelectionChanged) {
        this.onPhotoSelectionChanged = onPhotoSelectionChanged;
    }

    /**
     * 清除本次选择照片
     */
    public void clear() {
        if (mSelectedPhotoList != null) {
            mSelectedPhotoList.clear();
        }
        mPhotoSelectionHelper = null;
    }

    /**
     * 添加选取的照片
     * @param photo
     * @return 添加成功返回 true
     */
    public boolean addSelection(Photo photo) {
        boolean result = false;
        if (!mSelectedPhotoList.contains(photo)) {
            mSelectedPhotoList.add(photo);
            result = true;
            if (onPhotoSelectionChanged != null)
                onPhotoSelectionChanged.onPhotoSelectionChanged(getSelectedCount());
        }

        return result;
    }

    /**
     * 移除选中的照片
     * @param photo
     * @return
     */
    public boolean removeSelection(Photo photo) {
        boolean removed = false;
        synchronized (this) {
            removed = mSelectedPhotoList.remove(photo);
            if (onPhotoSelectionChanged != null)
                onPhotoSelectionChanged.onPhotoSelectionChanged(getSelectedCount());
        }
        return removed;
    }

    /**
     * 判断指定的照片是否选中
     * @param photo
     * @return
     */
    public boolean isSelected(Photo photo) {
        return mSelectedPhotoList.contains(photo);
    }

    /**
     * 批量添加选中的照片
     * @param photos
     */
    public void addSelections(ArrayList<Photo> photos) {

        final HashSet<Photo> currentSelectionsSet = new HashSet<Photo>(
                mSelectedPhotoList);
        for (final Photo photo : photos) {
            if (!currentSelectionsSet.contains(photo)) {
                mSelectedPhotoList.add(photo);
            }
        }
        if (onPhotoSelectionChanged != null)
            onPhotoSelectionChanged.onPhotoSelectionChanged(getSelectedCount());
    }

    /**
     * 重置选中的图片
     */
    public void reset() {
        synchronized (this) {
            mSelectedPhotoList.clear();
            if (onPhotoSelectionChanged != null)
                onPhotoSelectionChanged.onPhotoSelectionChanged(getSelectedCount());
        }
    }

    /**
     * 获取选中的图片集合
     * @return
     */
    public synchronized List<Photo> getSelected() {
        return new ArrayList<Photo>(mSelectedPhotoList);
    }

    /**
     * 获取选中图片的数量
     * @return
     */
    public synchronized int getSelectedCount() {
        return mSelectedPhotoList.size();
    }

    /**
     * 是否存在已选图片
     * @return
     */
    public synchronized boolean hasSelections() {
        return !mSelectedPhotoList.isEmpty();
    }


}
