package tyxo.baseappframe.utils.admin;

import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class AdminUtils {
	
	// ComponentName 描述 actvity receiver 的类名对象 <activity name <receiver name
	public static ComponentName deviceReceiver;
	
	/**
	 * 激活admin
	 */
	// 参考文档
	public static void active(Context context, String desc){
		// Intent激活系统的授权界面
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// ComponentName 描述 actvity receiver 的类名对象 <activity name <receiver name
		
		deviceReceiver = new ComponentName(context, MyAdminReciever.class.getName());
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceReceiver);// 配置在哪个receiver
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, desc);
		context.startActivity(intent);
	}
	
	/**
	 * 失活
	 */
	public static void inactive(Context context){
		// ComponentName 描述 actvity receiver 的类名对象;就是操作哪个receiver(找到要操作的对象)
		ComponentName deviceReceiver = new ComponentName(context, MyAdminReciever.class.getName());
		// 获得取DevicePolicyManager管理者
		DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		dpm.removeActiveAdmin(deviceReceiver);
	}
	
	/**
	 * 锁屏
	 */
	public static void lockScreen(Context context){
		// 获得取DevicePolicyManager管理者
		DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		
		dpm.resetPassword("6677", 0);
		dpm.lockNow();// 锁屏
	}
	
	public static boolean isActive(Context context){
		// 获得取DevicePolicyManager管理者
		DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName deviceReceiver = new ComponentName(context, MyAdminReciever.class.getName());
		return dpm.isAdminActive(deviceReceiver);
	}
	
	/**
	 * 恢复出厂设置
	 */
	public static void wipeData(Context context){
		// 获得取DevicePolicyManager管理者
		DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		// 恢复出厂设置
		dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//清除外部存储的数据
		
		// must have a "wipe-data" tag in the "uses-policies" section of its meta-data.??
		dpm.wipeData(DeviceAdminInfo.USES_POLICY_WIPE_DATA);//清除用户的数据
		//金山手机卫士: sms   contacts   图片  媒体
		//delte...
	}
}









