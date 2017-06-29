package tyxo.baseappframe.utils.bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class LocalCacheUtils {

	/**
	 * 本地缓存
	 */
	private String CACHE_DIR;// 本地缓存目录
	private MemoryCacheUtils memoryCacheUtils;
	
	public LocalCacheUtils (MemoryCacheUtils memoryCacheUtils){
//		CACHE_DIR = "/sdcard/zhbj71";
		CACHE_DIR = "/sdcard/zhbj71";
		this.memoryCacheUtils = memoryCacheUtils;
	}
	
	// 对外提供从本地获取图片的方法
	public Bitmap getBitmap(String url){
		Bitmap bitmap = null;
		try {
			// 把url做md5加密，作为文件名
			String fileName;
			fileName = MD5Encoder.encode(url);
			File file = new File(CACHE_DIR, fileName);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				// 往内存中存一份
				memoryCacheUtils.putBitmap(url, bitmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	// 对外提供往本地存图片的方法
	public void putBitmap(String url, Bitmap bitmap){
		try {
			// 把url做md5加密，作为文件名
			String fileName = MD5Encoder.encode(url);
			File file = new File(CACHE_DIR,fileName);// /sdcard/adsfas/zhbj71/jk;ljsljl
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {// 判断上级目录是否存在，不存在就需要创建
				parentFile.mkdirs();
			}
			// 把Bitmap对象持久化到本地
			OutputStream os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, os);//将流压缩成指定格式图片
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
