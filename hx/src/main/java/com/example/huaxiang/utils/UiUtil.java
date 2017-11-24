package com.example.huaxiang.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.huaxiang.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * <请描述这个类是干什么的>
 */
public class UiUtil {

    public static void setRoundImage(ImageView imageView, String imageUrl, int radius) {
        setRoundImage(imageView, imageUrl, R.drawable.defaut_image, R.drawable.defaut_image, radius);
    }

    public static void setRoundImage(ImageView imageView, String imageUrl, int loadingRes, int errorRes, int radius) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(errorRes)
                    .fitCenter()
                    .bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(imageView);
        } catch (Exception e) {
        }
    }


    public static void setImage(ImageView imageView, String imageUrl) {
        setImage(imageView, imageUrl, R.drawable.defaut_image, R.drawable.defaut_image);
    }

    public static void setImage(ImageView imageView, String imageUrl, int loadingRes, int errorRes) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(errorRes)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            Log.e("aaa", e.toString());

        }
    }

    public static void setCircleImage(final ImageView imageView, String imageUrl, int length) {
        setCircleImage(imageView, imageUrl, length, R.drawable.defaut_image, R.drawable.defaut_image);
    }

    public static void setCircleImage(final ImageView imageView, String imageUrl, int length, int loadingRes, int errorRes) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .error(errorRes)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .override(length, length)
                    .into(imageView);
        } catch (Exception e) {
        }
    }

    public static void setLocalImage(final ImageView imageView, String imageUrl) {
        setLocalImage(imageView, imageUrl, R.drawable.defaut_image, R.drawable.defaut_image);
    }

    public static void setLocalImage(final ImageView imageView, String imageUrl, int loadingRes, int errorRes) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .error(errorRes)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        } catch (Exception e) {
        }
    }

    public static void setImageListener(DiskCacheStrategy diskCacheStrategy, ImageView imageView, String imageUrl, int errorRes, final ResultListener listener) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context).load(imageUrl)
                    .diskCacheStrategy(diskCacheStrategy)
                    .error(errorRes)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (listener != null) listener.failed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (listener != null) listener.success();
                            return false;
                        }
                    }).into(imageView);
        } catch (Exception e) {
        }

    }

    public interface ResultListener {
        void success();

        void failed();
    }
}
