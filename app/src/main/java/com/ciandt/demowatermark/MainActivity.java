package com.ciandt.demowatermark;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public final class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int WATERMARK_OPACITY = 80;
    private static final int WATERMARK_TEXT_COLOR = Color.YELLOW;
    @SuppressWarnings("FieldCanBeLocal")
    private Target targetImpl1, targetImpl2; // strong reference

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadImageView1();
        loadImageView2();
    }

    private void loadImageView1() {
        targetImpl1 = new TargetImpl(
                this,
                WATERMARK_OPACITY,
                WATERMARK_TEXT_COLOR,
                new TargetImpl.WatermarkImageStatus() {
                    @Override
                    public void onWatermarkImageDone(Bitmap watermarkImage) {
                        ImageView imageView = (ImageView) MainActivity.this.findViewById(R.id.watermarkImage1);
                        imageView.setImageBitmap(watermarkImage);
                    }
                });


        new Picasso.Builder(this)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e(TAG, exception.getLocalizedMessage(), exception);
                    }
                })
                .build()
                .load(getString(R.string.cat_image_address))
                .error(R.drawable.ic_cloud_off)
                .placeholder(R.drawable.ic_cloud_queue)
                .into(targetImpl1);
    }

    private void loadImageView2() {
        targetImpl2 = new TargetImpl(
                this,
                WATERMARK_OPACITY,
                WATERMARK_TEXT_COLOR,
                new TargetImpl.WatermarkImageStatus() {
                    @Override
                    public void onWatermarkImageDone(Bitmap watermarkImage) {
                        ImageView imageView = (ImageView) MainActivity.this.findViewById(R.id.watermarkImage2);
                        imageView.setImageBitmap(watermarkImage);
                    }
                });



        new Picasso.Builder(this)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e(TAG, exception.getLocalizedMessage(), exception);
                    }
                })
                .build()
                .load(getString(R.string.cat_image_address))
                .transform(new CircleTransform())
                .error(R.drawable.ic_cloud_off)
                .placeholder(R.drawable.ic_cloud_queue)
                .into(targetImpl2);
    }
}

