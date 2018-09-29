package cn.droidlover.xdroidmvp.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by wanglei on 2016/11/28.
 */

public class GlideLoader implements ILoader {

    @Override
    public void init(Context mContext) {

    }

    private RequestManager getRequestManager(Context context) {
        if (context instanceof Activity) {
            return Glide.with((Activity) context);
        }
        return Glide.with(context);
    }

    private void load(Object model, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();
        RequestOptions requestOptions = wrapScaleType(options);

        DrawableTransitionOptions drawableTransitionOptions = new DrawableTransitionOptions().
                crossFade(new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build());

        getRequestManager(target.getContext())
                .load(model)
                .apply(requestOptions)
                .transition(drawableTransitionOptions)
                .into(target);
    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(url, target, options);
    }

    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        if (options == null) options = Options.defaultOptions();
        RequestOptions requestOptions = wrapScaleType(options);

        getRequestManager(context)
                .load(url)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }

                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (resource != null) {
                            if (callback != null) {
                                callback.onLoadReady(resource);
                            }
                        }
                    }

                });
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(resId, target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load("file:///android_asset/" + assetName, target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(file, target, options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }

    private RequestOptions wrapScaleType(Options options) {
        RequestOptions request = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.HIGH);

        if (options != null){
            if (options.scaleType != null) {
                if (options.loadingResId != Options.RES_NONE) {
                    request.placeholder(options.loadingResId);
                }
                if (options.loadErrorResId != Options.RES_NONE) {
                    request.error(options.loadErrorResId);
                }

                switch (options.scaleType) {
                    case MATRIX:
                    case FIT_XY:
                    case FIT_START:
                    case FIT_END:
                    case CENTER:
                    case CENTER_INSIDE:
                        break;

                    case FIT_CENTER:
                        request.fitCenter();
                        break;

                    case CENTER_CROP:
                        request.centerCrop();
                        break;
                }
            } else {
                request.centerCrop();
            }
        }else {
            request.centerCrop();
        }

        return request;
    }

    //加载圆形图片
    @Override
    public void loadCircle(String url, final ImageView target, Options options) {
        RequestOptions requestOptions = wrapScaleType(options);
        requestOptions.optionalCircleCrop();

        getRequestManager(target.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(target);

    }

    //加载圆形图片
    @Override
    public void loadCorner(String url, final ImageView target, int radius, Options options) {
        RequestOptions requestOptions = wrapScaleType(options);

        //设置图片圆角角度
        MultiTransformation multiTransformation = new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCorners(radius));
        requestOptions.transform(multiTransformation);

        getRequestManager(target.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(target);

    }
}
