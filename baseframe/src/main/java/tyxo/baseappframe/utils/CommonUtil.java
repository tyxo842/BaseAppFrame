package tyxo.baseappframe.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class CommonUtil {

	/**
	 * 在主线程执行任务
	 */
	public static void runOnUiThread(Runnable r){
		//BaseApplication.getMainHandler().post(r);
		new Handler().post(r);
	}
	
	/**
	 * 获取Resource对象
	 */
	public static Resources getResources(){
		return BaseApplication.getAppContext().getResources();
	}
	
	/**
	 * 获取drawable资源
	 */
	public static Drawable getDrawable(int resId){
		return getResources().getDrawable(resId);
	}
	
	/**
	 *  获取字符串资源
	 */
	public static String getString(int resId){
		return getResources().getString(resId);
	}
	
	/**
	 * 获取color资源
	 */
	public static int getColor(int resId){
		return getResources().getColor(resId);
	}
	
	/**
	 * 获取dimens资源
	 * 
	 */
	public static float getDimens(int resId) {
		return getResources().getDimension(resId);
	}
	
	/**
	 * 获取字符串数组资源
	 */
	public static String[] getStringArray(int resId){
		return getResources().getStringArray(resId);
	}
	
	/**
	 * 将指定的child从它 的父View中移除
	 */
	public static void removeSelfFromParent(View child){
		if (child!=null) {
			ViewParent parent = child.getParent();
			if (parent instanceof ViewGroup) {
				ViewGroup group = (ViewGroup) parent;
				group.removeView(child);//将子View从父View中移除
			}
		}
	}
}
