package com.hsq.dome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.dome.R;
import com.hsq.common.photopick.activity.PhotoSelectionActivity;
import com.hsq.common.photopick.adapter.BBaseAdapter;
import com.hsq.common.photopick.core.data.Photo;
import com.hsq.common.photopick.utils.ScreenUtil;
import com.hsq.common.photopick.widget.CustomImageView;
import com.hsq.common.photopick.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BBaseAdapter.OnItemClickListener<Photo>{

    private Button button;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.select_photo);
        recyclerView= (RecyclerView) findViewById(R.id.RecyclerView);
        adapter = new MyAdapter(this, null);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,R.drawable.divider,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, PhotoSelectionActivity.class);
                intent.putExtra(PhotoSelectionActivity.MAX_SELECTION_SIZE,9);
                startActivityForResult(intent,PhotoSelectionActivity.REQUEST_SELECTION_PHOTOS_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PhotoSelectionActivity.REQUEST_SELECTION_PHOTOS_CODE&&resultCode==PhotoSelectionActivity.RESPOND_SELECTION_PHOTOS_CODE){
            ArrayList<Photo>photos= (ArrayList<Photo>) data.getSerializableExtra(PhotoSelectionActivity.SELECTION_PHOTOS);
            if(photos!=null&&!photos.isEmpty()){
                adapter.setDatas(photos);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Photo data, int position) {

    }

    static class MyAdapter extends BBaseAdapter<Photo,MyAdapter.MyViewHolder>{

        public MyAdapter(Context context, List<Photo> datas) {
            super(context, datas);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=mInflater.inflate(R.layout.item_photo,parent,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder,final int position) {
            final Photo photo=getItemData(position);
            holder.render(photo);
            if(mItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemClickListener.onItemClick(holder,photo,position);
                    }
                });
            }
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{
            public CustomImageView imageView;
            public MyViewHolder(View itemView) {
                super(itemView);
                imageView=(CustomImageView)itemView.findViewById(R.id.iv_photo);
            }

            public void render(Photo photo){
                float scale= ScreenUtil.getScreenWidth(MyApplication.getApplication())/(float)photo.getW();
                imageView.getLayoutParams().width=ScreenUtil.getScreenWidth(MyApplication.getApplication());
                imageView.getLayoutParams().height=(int)(photo.getH()*scale);
                imageView.setBackgroundColor(Color.RED);
                imageView.setImageUrl("file://"+photo.getPhotoPath(), R.mipmap.ic_photo_bg);
            }
        }
    }
}
