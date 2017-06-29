package tyxo.baseappframe.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * BitmapTool： 位图处理工具类
 *
 * @Classname FastBlur
 * @date 2015-04-28 10：54
 */
public class BitmapTool {

    private static BitmapTool bitmapTool;

    public static BitmapTool getInstance() {
        if (bitmapTool == null) {
            bitmapTool = new BitmapTool();
        }
        return bitmapTool;
    }

    /**
     * 翻转图片
     *
     * @param bitmap
     * @param rotation 翻转角度 0-360‘
     * @return
     */
    public Bitmap rotateBitmap(Bitmap bitmap, int rotation) {
        rotation = rotation % 360;
        if (rotation == 0) {
            // do nothing.
            return bitmap;
        }

        boolean rotateDimension = (rotation > 45 && rotation < 135)
                || (rotation > 225 && rotation < 315);
        int width = !rotateDimension ? bitmap.getWidth() : bitmap.getHeight();
        int height = !rotateDimension ? bitmap.getHeight() : bitmap.getWidth();

        Bitmap newBitmap = null;
        try {
            newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        } catch (Throwable e) {
            // do nothing.
        }
        if (newBitmap == null || newBitmap == bitmap) {
            // no enough memory or original bitmap returns.
            return bitmap;
        }

        Canvas canvas = new Canvas(newBitmap);
        int dx = (width - bitmap.getWidth()) / 2, dy = (height - bitmap
                .getHeight()) / 2;
        if (dx != 0 || dy != 0) {
            canvas.translate(dx, dy);
        }
        canvas.rotate(rotation, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, 0, 0, null);
        // recycle prev bitmap.
        bitmap.recycle();

        return newBitmap;
    }

    private static Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

    /**
     * 创建缩略图
     *
     * @param bm       原图
     * @param filePath 缩略图存储位置
     */
    public void createThumbFile(Bitmap bm, String filePath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(65536);
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        File thumbfile = new File(filePath);
        if (thumbfile.exists()) {
            thumbfile.delete();
        }
        FileOutputStream fos = null;
        try {
            thumbfile.createNewFile();
            fos = new FileOutputStream(thumbfile);
            byte[] data = baos.toByteArray();
            fos.write(data);
            fos.flush();
        } catch (Exception e) {
        } finally {
            try {
                baos.close();
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e2) {
            }
        }
    }

    /**
     * 生成圆角图片
     *
     * @param bitmap
     * @param roundedPx 圆角半径长度
     * @return
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundedPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            if (roundedPx == 0) {
                roundedPx = bitmap.getWidth() / 2;
            }
            canvas.drawRoundRect(rectF, roundedPx, roundedPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    /**
     * 等倍压缩
     *
     * @param filePath
     * @param inSampleSize 压缩倍数
     * @return
     */
    public Bitmap ratioSampleSizeBitmap(String filePath, int inSampleSize) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param sourcePath 源图片路径
     * @param imagePath  压缩图片存储路径
     * @param imageName  压缩图片名
     * @param size       压缩质量百分比
     */
    public void compressBitmap(String sourcePath, String imagePath,
                               String imageName, int size) {
        if (size < 10 || size > 100)
            size = 80;

        try {
            Bitmap bitmap = ratioSampleSizeBitmap(sourcePath, 2);
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // sdcard可用
                File file1 = new File(imagePath);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                File file2 = new File(imagePath, imageName);
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileOutputStream bitmapWtriter = new FileOutputStream(file2);
                // 压缩图片，质量设置在80
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, size,
                        bitmapWtriter)) {
                    System.out.println("图片压缩保存成功");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取缩放图片
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public Bitmap getZoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 生成圆形图片
     *
     * @param bitmap 原图片
     * @return
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = bitmap.getWidth() / 2 < bitmap.getHeight() ? bitmap
                    .getWidth() : bitmap.getHeight();
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    /**
     * 模糊图
     *
     * @param bitmap
     * @return
     */
    public Bitmap getBlurBitmap(Bitmap bitmap) {
        float scaleFactor = 4;
        float radius = 2;
        Bitmap overlay = Bitmap.createBitmap(
                (int) (bitmap.getWidth() / scaleFactor),
                (int) (bitmap.getHeight() / scaleFactor),
                Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        return overlay;
    }

    public Bitmap drawableToBitmap1(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);

        Drawable d = new BitmapDrawable(bitmap);

        return bitmap;
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        return ((BitmapDrawable) drawable).getBitmap();
    }

    public Drawable bitmapToDrawable(Bitmap bitmap) {

        return new BitmapDrawable(bitmap);

    }

    /**
     * 将图片转换成Base64并压缩
     *
     * @param filePath     图片路径
     * @param inSampleSize inSampleSize 压缩倍数
     * @return
     */
    public String imageToBase64(String filePath, int inSampleSize) {
        Bitmap bitmap = ratioSampleSizeBitmap(filePath, 2);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * base64转bitmap
     */
    public Bitmap base64ToBitmap(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    /**
     * 保存为本地文件
     *
     * @param bitmap   图片
     * @param filename 文件路径
     * @param filename 文件名
     */
    public void saveLocalBitmap(Bitmap bitmap, String filepath, String filename) {
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将bitmap放入字节数组流中

                bos.flush();
                // 将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();
                byte[] bytes = bos.toByteArray();

                File pathFile = new File(filepath);
                if (!pathFile.exists())
                    pathFile.mkdirs();

                // new一个文件对象用来保存图片，默认保存当前工程根目录
                File imageFile = new File(pathFile, filename);

                // 创建输出流
                FileOutputStream outStream = new FileOutputStream(imageFile);
                // 写入数据
                outStream.write(bytes);
                // 关闭输出流
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     *
     */
    public void saveLocalFile(String filepath) throws Exception {

        File file = new File(filepath);
        // 如果图片存在本地缓存目录，则不去服务器下载

        // 从网络上获取图片
        URL url = new URL(filepath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        if (conn.getResponseCode() == 200) {

            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            // 返回一个URI对象

        }
    }

}
