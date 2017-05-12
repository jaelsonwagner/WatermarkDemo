package com.ciandt.demowatermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

/**
 * Created by jaelson on 18/03/17.
 */

final class TargetImpl implements Target {

    private final static String TAG = TargetImpl.class.getName();
    private final Context context;
    private int watermarkOpacity;
    private int watermarkTextColor;
    private WatermarkImageStatus watermarkImageStatus;

    TargetImpl(final Context context,
               int watermarkOpacity,
               int watermarkTextColor,
               final WatermarkImageStatus watermarkImageStatus) {

        this.context = context;
        this.watermarkOpacity = watermarkOpacity;
        this.watermarkTextColor = watermarkTextColor;
        this.watermarkImageStatus = watermarkImageStatus;

        if (watermarkImageStatus == null) {
            throw new NullPointerException(
                    String.format("%s should not be null!", WatermarkImageStatus.class.getName())
            );
        }

    }

    /**
     * Callback when an image has been successfully loaded.
     * <p>
     * <strong>Note:</strong> You must not recycle the bitmap.
     *
     * @param bitmap
     * @param from
     */
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Bitmap imageWithWatermark =
                ImageUtil.addDiagonalTextWaterMark(
                        bitmap,
                        context.getString(R.string.cat_says),
                        watermarkTextColor,
                        watermarkOpacity
                );

        if (watermarkImageStatus == null) {
            throw new NullPointerException(
                    String.format("%s should not be null!", WatermarkImageStatus.class.getName())
            );
        }

        this.watermarkImageStatus.onWatermarkImageDone(imageWithWatermark);

    }




    /**
     * Callback indicating the image could not be successfully loaded.
     * <p>
     * <strong>Note:</strong> The passed {@link Drawable} may be {@code null} if none has been
     * specified via {@link RequestCreator#error(Drawable)}
     * or {@link RequestCreator#error(int)}.
     *
     * @param errorDrawable
     */
    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Log.e(TAG, "onBitmapFailed");
    }

    /**
     * Callback invoked right before your request is submitted.
     * <p>
     * <strong>Note:</strong> The passed {@link Drawable} may be {@code null} if none has been
     * specified via {@link RequestCreator#placeholder(Drawable)}
     * or {@link RequestCreator#placeholder(int)}.
     *
     * @param placeHolderDrawable
     */
    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.i(TAG, "onPrepareLoad");
    }

    interface WatermarkImageStatus {
        void onWatermarkImageDone(Bitmap watermarkImage);
    }

}
