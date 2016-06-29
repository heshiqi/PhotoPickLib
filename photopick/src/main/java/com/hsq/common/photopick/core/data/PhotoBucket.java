package com.hsq.common.photopick.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoBucket {

	private String mBucketId;
	private String mBucketName;
	private String mCovePath;
	private boolean checked;

	private List<Photo> photos=new ArrayList<Photo>();

	public PhotoBucket(String id, String name, String covePath) {
		mBucketId = id;
		mBucketName = name;
		mCovePath = covePath;
	}

	public String getId() {
		return mBucketId;
	}

	public String getName() {
		return mBucketName;
	}

	public String getmBucketId() {
		return mBucketId;
	}

	public void setmBucketId(String mBucketId) {
		this.mBucketId = mBucketId;
	}

	public String getmBucketName() {
		return mBucketName;
	}

	public void setmBucketName(String mBucketName) {
		this.mBucketName = mBucketName;
	}

	public String getmCovePath() {
		return mCovePath;
	}

	public void setmCovePath(String mCovePath) {
		this.mCovePath = mCovePath;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public void addPhoto(Photo photo){
		photos.add(photo);
	}
	
	public void clearAll(){
		photos.clear();
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mBucketId == null) ? 0 : mBucketId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhotoBucket other = (PhotoBucket) obj;
		if (mBucketId == null) {
			if (other.mBucketId != null)
				return false;
		} else if (!mBucketId.equals(other.mBucketId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return mBucketName;
	}


	public static PhotoBucket getAllPhotosBucket(String name, String covePath, List<Photo> photos) {
		PhotoBucket all = new PhotoBucket(null, name, covePath);
		all.setPhotos(photos);
		return all;
	}

}
