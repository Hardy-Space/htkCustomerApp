package com.hl.htk_customer.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ImageUtils {



    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }



    //压缩图片
    public static Bitmap BitmapScale(String filePath) {

        if (TextUtils.isEmpty(filePath))
            return null;
        File file = new File(filePath);
        if (file.isFile()) {
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options1);
            Bitmap bitmap = null;
            if (options1.outWidth > 480 || options1.outHeight > 480) {
                options1.inSampleSize = ImageUtils.calculateInSampleSize(options1, 480, 480);  //110,160：转换后的宽和高，具体值会有些出入
                options1.inJustDecodeBounds = false;
                options1.inInputShareable = true;
                options1.inPurgeable = true;
                options1.inPreferredConfig = Bitmap.Config.RGB_565;

                bitmap = BitmapFactory.decodeFile(filePath, options1);
            } else {
                bitmap = BitmapFactory.decodeFile(filePath);
            }

            return bitmap;
        }
        return null;

    }



    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            float heightRatio = (float) height
                    / (float) reqHeight;
            float widthRatio = (float) width / (float) reqWidth;

            float val = heightRatio < widthRatio ? widthRatio : heightRatio;
            if (val % 2 >= 1.0f) {
                inSampleSize = Math.round(val) + 1;
            } else {
                inSampleSize = Math.round(val);
            }
        }

        return inSampleSize;
    }



    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }



}
