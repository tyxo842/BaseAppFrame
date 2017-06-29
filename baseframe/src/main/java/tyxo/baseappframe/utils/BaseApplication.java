package tyxo.baseappframe.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by tyxo on 2016/9/21 10: 41.
 * Mail      tyxo842@163.com
 * Describe : 可将此作为基类的 application ,再继承进行扩展.
 */
public class BaseApplication extends Application {

    private static Context context;
    //private static Handler mainHandler;//主线程的handler

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //mainHandler = new Handler();
    }

    /**
     * 获取全局的context
     */
    public static Context getAppContext(){
        return context;
    }

    /*public static void initImageLoader(BaseApplication application) {

        //This configuration tuning is custom. You can tune every option, you may tune some of them,
        //or you can create default configuration by
        //    ImageLoaderConfiguration.createDefault(this);
        //method.

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY-2);//配置下载图片的线程优先级
        config.denyCacheImageMultipleSizesInMemory();//不会在内存中缓存多个大小的图片
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());//为了保证图片名称唯一
        config.diskCacheSize(50*1024*1024);// 50 MiB
        //内存缓存大小默认是：app可用内存的1/8
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
		//ImageLoader.getInstance().init( ImageLoaderConfiguration.createDefault(this));
    }*/

    /**获取全局的主线程的handler*/
    /*public static Handler getMainHandler(){
        return mainHandler;
    }*/
}
