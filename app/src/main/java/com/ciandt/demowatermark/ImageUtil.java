package com.ciandt.demowatermark;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

/**
 * Created by jaelson on 18/03/17.
 */

class ImageUtil {

    private static final float PERCENT = 0.10f;

    private ImageUtil() {
    }

    static Bitmap addDiagonalTextWaterMark(Bitmap src,
                                           String text,
                                           int color,
                                           int opacity) {

        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());

        Point baseLineBegin = new Point(0, src.getHeight());
        Point baseLineEnd = new Point(src.getWidth(), 0);

        // diagonal base line
        Path path = new Path();
        path.moveTo(baseLineBegin.x, baseLineBegin.y);
        path.lineTo(baseLineEnd.x, baseLineEnd.y);

        // get bounds from path
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        float textSize = rectF.width() * PERCENT;

        Rect bounds = new Rect();

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        paint.setAlpha(opacity);
        paint.getTextBounds(text, 0, text.length(), bounds);

        float middleFontHeight = bounds.height() >> 1; // equivalent to bounds.height() / 2, but more faster

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        // just for debug
//        canvas.drawLine(
//                baseLineBegin.x, baseLineBegin.y,
//                baseLineEnd.x, baseLineEnd.y,
//                paint
//        );
        canvas.drawTextOnPath(text, path, 0, middleFontHeight, paint);

        return result;
    }

}
