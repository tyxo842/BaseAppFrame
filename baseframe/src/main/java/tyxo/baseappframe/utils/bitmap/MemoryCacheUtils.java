package tyxo.baseappframe.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存:对外提供获取与存放的方法
 *
 */
//初始化的时候就得到图片的大小
public class MemoryCacheUtils {

	private LruCache<String, Bitmap> lruCache;//LRUCache类:最近最少使用-->防内存溢出
	
	public MemoryCacheUtils(){
		int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);// 容量:取当前(手机)应用最大内存的1/8
		lruCache = new LruCache<String, Bitmap>(maxSize){
			// 返回放进去的每一张图片的大小(以像素为单位):每行的大小,乘以行数;-->这样,存入的图片总大小也知道了,将内存溢出时,开始删图片;
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return (value.getRowBytes())*(value.getHeight());
			}
		};
	}
	
	// 对外提供从内存缓存获取图片的方法
	public Bitmap getBitmap(String url){
		return lruCache.get(url);
	}
	
	// 对外提供往内存缓存存图片的方法
	public Bitmap putBitmap(String url, Bitmap bitmap){
		return lruCache.put(url, bitmap);
	}
}
