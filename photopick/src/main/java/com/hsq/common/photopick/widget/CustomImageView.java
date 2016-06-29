package com.hsq.common.photopick.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hsq.common.photopick.utils.ScreenUtil;

/**
 * Created by hsq on 2016/5/12.
 */
public class CustomImageView extends SimpleDraweeView {

    public static class AHImageInfo {
        public int width;
        public int height;

    }

    /**
     * 加载图片回调
     */
    public interface ImageLoadingListener {
        /**
         * 加载成功
         *
         * @param id
         * @param ImageInfo
         */
        public void onSuccess(String id, AHImageInfo ImageInfo);

        /**
         * 加载失败
         *
         * @param id
         * @param throwable
         */
        public void onFailure(String id, Throwable throwable);
    }

    public CustomImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 设置图片
     * @param url  图片的路径
     * @param placeholderImage  加载时显示的图片
     * @param loadingListener   加载图片的事件监听{@link ImageLoadingListener}
     */
    public void setImageUrl(String url, int placeholderImage, final ImageLoadingListener loadingListener) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id, ImageInfo imageInfo, Animatable anim) {
                AHImageInfo info = new AHImageInfo();
                if (imageInfo != null) {
                    info.width = imageInfo.getWidth();
                    info.height = imageInfo.getHeight();
                }
                if (loadingListener != null)
                    loadingListener.onSuccess(id, new AHImageInfo());
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                if (loadingListener != null)
                    loadingListener.onFailure(id, throwable);
            }
        };
       setPlaceholderImage(placeholderImage);
        Uri uri = Uri.parse(url);
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(true)
                ;
        int w=getLayoutParams().width;
        int h=getLayoutParams().height;
        if(w<=0){
            w= ScreenUtil.getScreenWidth(getContext())/2;
        }
        if(h<=0){
            h=ScreenUtil.getScreenHeight(getContext())/2;
        }
        requestBuilder.setResizeOptions(new ResizeOptions(w,h));
        ImageRequest imageRequest=requestBuilder.build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setControllerListener(controllerListener)
                .setOldController(getController())
                .setAutoPlayAnimations(true)
                .build();
        setController(controller);
    }

    /**
     * 设置图片显示圆形
     */
    public void setAsCircle() {
        RoundingParams roundingParams = getMHierarchy().getRoundingParams();
        if (roundingParams == null) {
            roundingParams = RoundingParams.asCircle();
        }
        getMHierarchy().setRoundingParams(roundingParams);
    }

    /**
     * @param url              图片加载路径
     * @param placeholderImage 加载中时显示的url
     */
    public void setImageUrl(String url, int placeholderImage) {
        if(url==null)
            return;
        setPlaceholderImage(placeholderImage);
        if(url.startsWith("file://")){
            setLocalImageUrl(url);
        }else{
            setNetImageUrl(url);
        }
    }

    /**
     * 加载本地图片路径
     * @param path
     */
    public void setLocalImageUrl(String path){
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(path)).setAutoRotateEnabled(true);
        int w=getLayoutParams().width;
        int h=getLayoutParams().height;
        if(w<=0){
            w= ScreenUtil.getScreenWidth(getContext())/2;
        }
        if(h<=0){
            h=ScreenUtil.getScreenHeight(getContext())/2;
        }
        requestBuilder.setResizeOptions(new ResizeOptions(w,h));
        ImageRequest imageRequest=requestBuilder.build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        setController(controller);
    }

    /**
     * 加载本地图片路径
     * @param path
     */
    public void setLocalImageUrl(String path,int width,int height){
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(path))
                .setResizeOptions(new ResizeOptions(width,height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        setController(controller);
    }

    /**
     * 加载图片
     * @param url 图片路径
     * @param drawable  图片加载时显示的图
     */
    public void setImageUrl(String url, Drawable drawable) {
        if(url==null)
            return;
        setPlaceholderImage(drawable);
        if(url.startsWith("file://")){
            setLocalImageUrl(url);
        }else{
            setNetImageUrl(url);
        }
    }

    /**
     * 加载网络图片
     * @param url
     */
    public void setNetImageUrl(String url){
        Uri uri = Uri.parse(url);
        setImageURI(uri);
    }

    public GenericDraweeHierarchy getMHierarchy() {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if (hierarchy == null) {
            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getResources());
            hierarchy = builder.build();
            setHierarchy(hierarchy);
        }
        return hierarchy;
    }

    /**
     * 设置圆角半径
     *
     * @param radius
     */
    public void setRadius(float radius) {
        GenericDraweeHierarchy hierarchy = getMHierarchy();
        RoundingParams roundingParams = hierarchy.getRoundingParams();
        if (roundingParams == null) {
            roundingParams = RoundingParams.fromCornersRadius(radius);
        }
        hierarchy.setRoundingParams(roundingParams);
    }

    private void setPlaceholderImage(Drawable drawable){
        if (drawable != null) {
            GenericDraweeHierarchy hierarchy = getHierarchy();
            if (hierarchy == null) {
                GenericDraweeHierarchyBuilder builder =
                        new GenericDraweeHierarchyBuilder(getResources());
                hierarchy = builder.build();
                setHierarchy(hierarchy);
            }
            hierarchy.setPlaceholderImage(drawable);
        }
    }

    private void setPlaceholderImage(int drawable){
        if (drawable != 0) {
            GenericDraweeHierarchy hierarchy = getHierarchy();
            if (hierarchy == null) {
                GenericDraweeHierarchyBuilder builder =
                        new GenericDraweeHierarchyBuilder(getResources());
                hierarchy = builder.build();
                setHierarchy(hierarchy);
            }
            hierarchy.setPlaceholderImage(drawable);
        }
    }
}
