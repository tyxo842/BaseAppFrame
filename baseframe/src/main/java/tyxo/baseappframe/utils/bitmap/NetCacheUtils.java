package tyxo.baseappframe.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  网络缓存:从网络中获取图片,展示
 */
public class NetCacheUtils {

	private ExecutorService pool;//线程池:存放指定数量的线程数,复用线程对象,防止创建过多;-->listview滑动时,每个条目都会产生一个子线程,条目过多,子线程过多,会产生卡顿现象;
	private MyHandler handler;
	
	private final int RESCODE_SUCCESS = 1;
	private MemoryCacheUtils memoryCacheUtils;
	private LocalCacheUtils localCacheUtils;
	private ListView lv_photo_list;
	
	public NetCacheUtils(MemoryCacheUtils memoryCacheUtils, LocalCacheUtils localCacheUtils){
		pool = Executors.newFixedThreadPool(5);
		this.memoryCacheUtils = memoryCacheUtils;
		this.localCacheUtils = localCacheUtils;
	}
	
	// 获取图片，并且展示
	public void display(String url, ImageView imageView, ListView lv_photo_list){
		pool.execute(new ImageRunnable(url, imageView));
		handler = new MyHandler();
		this.lv_photo_list = lv_photo_list;
	}
	
	// 任务:访问网络，获取图片
	class ImageRunnable implements Runnable{
		
		private String mUrl;
		private ImageView mImageView;
		private int position;// 线程一旦创建，马上获取当前控件身上的位置
		
		public ImageRunnable(String url, ImageView imageView){
			this.mUrl = url;
			this.mImageView = imageView;
			this.position = (Integer) imageView.getTag();
		}

		@Override
		public void run() {
			// 访问网络，获取图片
			try {
				Thread.sleep(2000);
				HttpURLConnection conn = (HttpURLConnection) new URL(mUrl).openConnection();
				conn.connect();// 建立连接
				int resCode = conn.getResponseCode();
				if (resCode==200) {
					InputStream is = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					// 展示图片
					Message.obtain(handler, RESCODE_SUCCESS,new Result(position, bitmap)).sendToTarget();
					// 往内存和本地都存一份
					memoryCacheUtils.putBitmap(mUrl, bitmap);
					localCacheUtils.putBitmap(mUrl, bitmap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class Result{
		public ImageView imageView;
		public Bitmap bitmap;
		private int position;
		
		public Result(ImageView imageView, Bitmap bitmap){
			this.imageView = imageView;
			this.bitmap = bitmap;
		}
		
		public Result(int position, Bitmap bitmap){
			this.position  = position;
			this.bitmap = bitmap;
		}
	}
	
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			/*if(msg.what == RESCODE_SUCCESS){
				Result res = (Result) msg.obj;
				res.imageView.setImageBitmap(res.bitmap);
			}*/
			if (msg.what==RESCODE_SUCCESS) {
				Result res = (Result) msg.obj;
				// 根据线程开启时的位置，在当前界面中找ImageView
				/**
				 * PhotoDetailPager中,getView方法中118行,注解后的settag-->在此处得到;
				 * 	界面滑动中:如果根据tag找不到(条目么有显示),则imageView为null;
				 * 	界面滑动停止:找到了(listview下面的条目,新复用的),就显示出来
				 */
				ImageView imageView = (ImageView) lv_photo_list.findViewWithTag(res.position);
				if (imageView!=null) {
					imageView.setImageBitmap(res.bitmap);
				}
			}
		}
	}
}
