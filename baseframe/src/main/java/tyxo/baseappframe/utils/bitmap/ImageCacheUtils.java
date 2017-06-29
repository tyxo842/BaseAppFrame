package tyxo.baseappframe.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ListView;

import tyxo.baseappframe.utils.log.TLog;

public class ImageCacheUtils {

	private MemoryCacheUtils memoryCacheUtils;
	private LocalCacheUtils localCacheUtils;
	private NetCacheUtils netCacheUtils;
	
	public ImageCacheUtils(){
		memoryCacheUtils = new MemoryCacheUtils();
		localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
		netCacheUtils = new NetCacheUtils(memoryCacheUtils, localCacheUtils);
	}
	
	public void display(ImageView imageView, String url, ListView lv_photo_list){
		/**
		 * 1、从内存缓存中取图片：取到就展示 2、取不到，从本地缓存中取图片：取到就展示 3、取不到，从网络中获取图片： 3.1、取到就展示
		 * 3.2、往内存和本地都存一份
		 */
		Bitmap bitmap = null;
		bitmap = memoryCacheUtils.getBitmap(url);// 1、从内存缓存中取图片
		if (bitmap!=null) {
			imageView.setImageBitmap(bitmap);
			TLog.i("从内存缓存中取图片");
			return;
		}
		
		bitmap = localCacheUtils.getBitmap(url);// 2、从本地缓存中取图片
		if (bitmap!=null) {
			imageView.setImageBitmap(bitmap);
			TLog.i("从本地缓存中取图片");
			return;
		}
		
		netCacheUtils.display(url, imageView, lv_photo_list);
		TLog.i("从网络中获取图片");
		// 3从网络中获取图片，获取后直接展示
	}
}
