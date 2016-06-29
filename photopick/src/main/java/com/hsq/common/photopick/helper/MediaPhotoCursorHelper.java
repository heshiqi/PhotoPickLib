package com.hsq.common.photopick.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;

import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.core.data.PhotoBucket;
import com.hsq.common.photopick.utils.ImageUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by heshiqi on 16/5/8.
 */
public class MediaPhotoCursorHelper {

	public static final String[] PHOTOS_PROJECTION = { Images.Media._ID, Images.Media.MINI_THUMB_MAGIC,
			Images.Media.DATA, Images.Media.BUCKET_DISPLAY_NAME, Images.Media.BUCKET_ID , Images.Media.WIDTH, Images.Media.HEIGHT};
	public static final String PHOTOS_ORDER_BY = Images.Media.DATE_ADDED + " desc";

	public static final Uri MEDIA_STORE_CONTENT_URI = Images.Media.EXTERNAL_CONTENT_URI;

	public static final String SELECTION = Images.Media.BUCKET_DISPLAY_NAME + " not like ? " + " and "
//			+ Images.Media.SIZE + " is not null "
	 + Images.Media.SIZE + " >51200";

	public static final String[] SELECTIONARGS = new String[] { ".%" };

	public static void photosCursorToBucketList(Cursor cursor, ArrayList<PhotoBucket> items, PhotoBucket all) {
		final HashSet<String> bucketIds = new HashSet<String>();
		final int bucketidColumn = cursor.getColumnIndex(ImageColumns.BUCKET_ID);
		final int _idColumn = cursor.getColumnIndex(ImageColumns._ID);
		final int nameColumn = cursor.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME);
		final int dataColumn = cursor.getColumnIndex(ImageColumns.DATA);
		final int widthColumn  = cursor.getColumnIndex(ImageColumns.WIDTH);
		final int heightColumn  = cursor.getColumnIndex(ImageColumns.HEIGHT);
		if (cursor.moveToFirst()) {
			do {
				try {
					final String bucketId = cursor.getString(bucketidColumn);
					final long id = cursor.getLong(_idColumn);
					final String name = cursor.getString(nameColumn);
					final String path = cursor.getString(dataColumn);
					final int width  = cursor.getInt(widthColumn);
					final int height  = cursor.getInt(heightColumn);
					PhotoBucket bucket = new PhotoBucket(bucketId, name, path);
					if (bucketIds.add(bucketId)) {
						items.add(bucket);
					}
					items.get(items.indexOf(bucket)).addPhoto(new Photo(id, path,Photo.TYPE_PHOTO));
					Photo photo=new Photo(id, path,Photo.TYPE_PHOTO);
					photo.setRotate(ImageUtils.getRawBitmapRotate(path));
					photo.setW(width);
					photo.setH(height);
					all.addPhoto(photo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
		}
	}

	public static Cursor openPhotosCursor(Context context, Uri contentUri) {
		return context.getContentResolver().query(contentUri, PHOTOS_PROJECTION, SELECTION, SELECTIONARGS,
				PHOTOS_ORDER_BY);
	}

}
