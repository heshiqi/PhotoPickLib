package com.hsq.common.photopick.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hsq.common.photopick.R;
import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.helper.PhotoSelectionHelper;


/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoItemLayout extends CheckableFrameLayout implements
		View.OnClickListener {

	private final CustomImageView mImageView;
	private final CheckableImageView mButton;
	private final View mCoverView;

	private Photo mSelection;

	public PhotoItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater.from(context).inflate(R.layout.item_grid_photo_internal,
				this);

		mImageView = (CustomImageView) findViewById(R.id.iv_photo);

		mButton = (CheckableImageView) findViewById(R.id.civ_button);
		mButton.setOnClickListener(this);
		mCoverView = findViewById(R.id.cover);
	}

	public CustomImageView getImageView() {
		return mImageView;
	}

	public void setShowCheckbox(boolean visible) {
		if (visible) {
			mButton.setVisibility(View.VISIBLE);
			mButton.setOnClickListener(this);
		} else {
			mButton.setVisibility(View.GONE);
			mButton.setOnClickListener(null);
		}
	}

	public void onClick(View v) {
		if (null != mSelection) {
			if(!isChecked()&&PhotoSelectionHelper.getInstance().isCompleteSelection())
				return;
			toggle();
			if (isChecked()) {
				PhotoSelectionHelper.getInstance().addSelection(mSelection);
			} else {
				PhotoSelectionHelper.getInstance().removeSelection(mSelection);
			}
		}
	}

	@Override
	public void setChecked(final boolean b) {
		super.setChecked(b);
		mCoverView.setVisibility(b ? View.VISIBLE : View.GONE);
		if (View.VISIBLE == mButton.getVisibility()) {
			mButton.setChecked(b);
		}
	}

	public Photo getPhotoSelection() {
		return mSelection;
	}

	public void setPhotoSelection(Photo selection) {
		if (mSelection != selection) {
			mSelection = selection;
		}
	}

}
