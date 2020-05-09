package com.steelkiwi.cropiwa.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;
import com.steelkiwi.cropiwa.shape.CropIwaShapeMask;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Yaroslav Polyakov on 22.03.2017.
 * https://github.com/polyak01
 */

class CropImageTask extends AsyncTask<Void, Void, Throwable> {

    private Context context;
    private CropArea cropArea;
    private CropIwaShapeMask mask;
    private Uri srcUri;
    private long MAX_LENGTH = 2L * 1024L * 1024L;
    private CropIwaSaveConfig saveConfig;

    public CropImageTask(
            Context context, CropArea cropArea, CropIwaShapeMask mask,
            Uri srcUri, CropIwaSaveConfig saveConfig) {
        this.context = context;
        this.cropArea = cropArea;
        this.mask = mask;
        this.srcUri = srcUri;
        this.saveConfig = saveConfig;
    }

    @Override
    protected Throwable doInBackground(Void... params) {
        try {
            Bitmap bitmap = CropIwaBitmapManager.get().loadToMemory(
                    context, srcUri, saveConfig.getWidth(),
                    saveConfig.getHeight());

            if (bitmap == null) {
                return new NullPointerException("Failed to load bitmap");
            }

            Bitmap cropped = cropArea.applyCropTo(bitmap);

            cropped = mask.applyMaskTo(cropped);

            Uri dst = saveConfig.getDstUri();
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//            cropped.compress(saveConfig.getCompressFormat(), saveConfig.getQuality(), bs);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
//            FileOutputStream os = new FileOutputStream(dst.getPath(), false);//将压缩后的图片保存的本地上指定路径中
//            os.write(bs.toByteArray());
//            os.flush();
//            os.close();
//            bs.close();

            // 设置参数
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
//            BitmapFactory.decodeFile(dst.getPath());
//            int height = options.outHeight;
//            int width = options.outWidth;
//            int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
//            int minLen = Math.min(height, width); // 原图的最小边长
//            if (minLen > 100f) { // 如果原始图像的最小边长大于 saveConfig.getMaxWidth()
//                float ratio = (float) minLen / 100f; // 计算像素压缩比例
//                inSampleSize = (int) ratio;
//            }
//            options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
//            options.inSampleSize = inSampleSize;
//            Bitmap bm = BitmapFactory.decodeFile(dst.getPath(), options); // 解码文件
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            cropped.compress(saveConfig.getCompressFormat(), saveConfig.getQuality(), baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
//            FileOutputStream fos = new FileOutputStream(dst.getPath(),false);//将压缩后的图片保存的本地上指定路径中
//            fos.write(baos.toByteArray());
//            fos.flush();
//            fos.close();
//            CropIwaUtils.closeSilently(baos);
//            if (!bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
//            if (!cropped.isRecycled()) {
//                cropped.recycle();
//            }
//            if (!bm.isRecycled()) {
//                bm.recycle();
//            }

//            Bitmap cropped = cropArea.applyCropTo(bitmap);
//            cropped = mask.applyMaskTo(cropped);
//            Uri dst = saveConfig.getDstUri();
            Log.i("Crop","裁剪成功");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            cropped.compress(saveConfig.getCompressFormat(), saveConfig.getQuality(), baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
            FileOutputStream fos = new FileOutputStream(dst.getPath());//将压缩后的图片保存的本地上指定路径中
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            Log.i("Crop","质量压缩保存成功");

            BitmapFactory.Options newOpts;
            if (baos.toByteArray().length > MAX_LENGTH) {
                baos.reset();
                newOpts = new BitmapFactory.Options();
                newOpts.inJustDecodeBounds = true;//只读边,不读内容
                BitmapFactory.decodeFile(dst.getPath(), newOpts);
                newOpts.inJustDecodeBounds = false;
                int width = newOpts.outWidth;
                int height = newOpts.outHeight;
                float maxSize = saveConfig.getMaxWidth();
                int be = 1;
                if (width >= height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
                    be = (int) (newOpts.outWidth / maxSize);
                    be++;
                } else if (width < height && height > maxSize) {
                    be = (int) (newOpts.outHeight / maxSize);
                    be++;
                }
                newOpts.inSampleSize = be;//设置采样率
                newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
                newOpts.inPurgeable = true;// 同时设置才会有效
                newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
                cropped = BitmapFactory.decodeFile(dst.getPath(), newOpts);
                cropped.compress(saveConfig.getCompressFormat(), saveConfig.getQuality(), baos);
                Log.i("Crop","压缩后读取完成");

                FileOutputStream os = new FileOutputStream(dst.getPath());//将压缩后的图片保存的本地上指定路径中
                os.write(baos.toByteArray());
                os.flush();
                os.close();
                Log.i("Crop","压缩后保存完成");
            }

            baos.close();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (!cropped.isRecycled()) {
                cropped.recycle();
            }
//            if (cropped.getByteCount() > 3 * 1024 * 1024) {
//                float zoom = (float) Math.sqrt(3 * 1024 * 1024 / (float) baos.toByteArray().length);
//
//                Matrix matrix = new Matrix();
//                matrix.setScale(zoom, zoom);
//
//                cropped = Bitmap.createBitmap(cropped, 0, 0, cropped.getWidth(), cropped.getHeight(), matrix, true);
//                baos.reset();
//                cropped.compress(saveConfig.getCompressFormat(), 75, baos);
//                while (baos.toByteArray().length > 3 * 1024 * 1024) {
//                    matrix.setScale(0.8f, 0.8f);
//                    cropped = Bitmap.createBitmap(cropped, 0, 0, cropped.getWidth(), cropped.getHeight(), matrix, true);
//                    baos.reset();
//                    cropped.compress(saveConfig.getCompressFormat(), 75, baos);
//                }
//            }

//            CropIwaUtils.closeSilently(baos);
//            CropIwaUtils.closeSilently(fos);
//            if (!bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
//            if (!cropped.isRecycled()) {
//                cropped.recycle();
//            }

        } catch (IOException e) {
            e.printStackTrace();
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Throwable throwable) {
        if (throwable == null) {
            CropIwaResultReceiver.onCropCompleted(context, saveConfig.getDstUri());
        } else {
            CropIwaResultReceiver.onCropFailed(context, throwable);
        }
    }
}