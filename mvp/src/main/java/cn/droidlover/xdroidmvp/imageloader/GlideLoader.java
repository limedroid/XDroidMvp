package cn.droidlover.xdroidmvp.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
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

    private void load(Context context, Object model, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(options.loadingResId)
                .error(options.loadErrorResId)
                .priority(Priority.HIGH);

        getRequestManager(context)
                .load(model)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(target);
    }

    @Override
    public void loadNet(Context context, ImageView target, String url, Options options) {
        load(context, url, target, options);
    }

    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        if (options == null) options = Options.defaultOptions();
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(options.loadingResId)
                .error(options.loadErrorResId)
                .priority(Priority.HIGH);

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
    public void loadResource(Context context,ImageView target, int resId, Options options) {
        load(context, resId, target, options);
    }

    @Override
    public void loadAssets(Context context, ImageView target, String assetName, Options options) {
        load(context, "file:///android_asset/" + assetName, target, options);
    }

    @Override
    public void loadFile(Context context,ImageView target, File file, Options options) {
        load(context, file, target, options);
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
}
