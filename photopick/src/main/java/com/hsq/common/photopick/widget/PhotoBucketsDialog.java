package com.hsq.common.photopick.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.hsq.common.photopick.R;
import com.hsq.common.photopick.adapter.BBaseAdapter;
import com.hsq.common.photopick.adapter.PhotoBucketAdapter;
import com.hsq.common.photopick.core.presenter.PhotoSelectionPresenter;
import com.hsq.common.photopick.utils.ScreenUtil;

/**
 * Created by heshiqi on 16/5/8.
 */
public class PhotoBucketsDialog extends Dialog {

	private Context context;
	private RecyclerView mRecyclerView;
	private PhotoBucketAdapter mBucketAdapter;
	private PhotoSelectionPresenter mSelectionPresenter;

	private BBaseAdapter.OnItemClickListener onItemClickListener;
	public void setOnItemClickListener(BBaseAdapter.OnItemClickListener listener){
		this.onItemClickListener=listener;
	}

	public PhotoBucketsDialog(Context context, int themeResId,PhotoSelectionPresenter selectionPresenter) {
		super(context, themeResId);
		this.context = context;
		this.mSelectionPresenter=selectionPresenter;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.layout_photo_buckets);
		Window window = getWindow();
		LayoutParams wlp = window.getAttributes();
		wlp.width = LayoutParams.MATCH_PARENT;
		wlp.gravity = Gravity.BOTTOM;
		window.setAttributes(wlp);
		mRecyclerView=(RecyclerView)findViewById(R.id.rv_photo_buckets);
		mRecyclerView.getLayoutParams().height= (int) (ScreenUtil.getScreenHeight(getContext())- ScreenUtil.getPixels(getContext(),140));
		mBucketAdapter = new PhotoBucketAdapter(getContext(), mSelectionPresenter.getPhotoBuckets());
		mBucketAdapter.setOnItemClickListener(onItemClickListener);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
		mRecyclerView.setAdapter(mBucketAdapter);
		setCanceledOnTouchOutside(true);

	}

	@Override
	public void show() {
		if(mBucketAdapter!=null)
		mBucketAdapter.notifyDataSetChanged();
		super.show();
	}
}
