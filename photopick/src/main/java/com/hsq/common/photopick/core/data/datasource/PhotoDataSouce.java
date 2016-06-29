package com.hsq.common.photopick.core.data.datasource;

import android.content.Context;
import android.database.Cursor;

import com.hsq.common.photopick.R;
import com.hsq.common.photopick.core.data.PhotoBucket;
import com.hsq.common.photopick.helper.MediaPhotoCursorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoDataSouce {

    public List<PhotoBucket> getPhotoBuckets(Context context){

        ArrayList<PhotoBucket> result = null;

        if (null != context) {
            // Add 'All Photos' item
            result = new ArrayList<PhotoBucket>();
            final PhotoBucket all = new PhotoBucket("ALL", context.getString(R.string.album_all), "");
            result.add(all);
            Cursor cursor = MediaPhotoCursorHelper.openPhotosCursor(context,
                    MediaPhotoCursorHelper.MEDIA_STORE_CONTENT_URI);

            if (null != cursor) {
                MediaPhotoCursorHelper.photosCursorToBucketList(cursor, result, all);
                cursor.close();
            }
            if (result.size() > 1)
                all.setmCovePath(result.get(1).getmCovePath());
        }

        return result;

    }


}
