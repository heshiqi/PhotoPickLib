package com.hsq.common.photopick.core.data;

import java.io.Serializable;

/**
 * Created by heshiqi on 16/5/8.
 */
public class Photo implements Serializable {

    public final static int TYPE_CAMERA = 1;

    public final static int TYPE_PHOTO = 1 << 1;

    private String mPhotoPath = "";

    private long id;

    private int type;
    private int w;
    private int h;
    private int rotate;

    public Photo(long id, String covePath,int type) {
        this.id = id;
        this.mPhotoPath = covePath;
        this.type=type;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.mPhotoPath = mPhotoPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return mPhotoPath != null ? mPhotoPath.equals(photo.mPhotoPath) : photo.mPhotoPath == null;

    }

    @Override
    public int hashCode() {
        return mPhotoPath != null ? mPhotoPath.hashCode() : 0;
    }
}
