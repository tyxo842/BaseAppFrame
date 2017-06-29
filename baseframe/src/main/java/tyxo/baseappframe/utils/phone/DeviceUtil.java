package tyxo.baseappframe.utils.phone;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class DeviceUtil {
	private static PackageManager pm;
	private static PackageInfo packageInfo;

	/**
	 * 获得版本名称
	 */
	public static String getVersionName(Context context){
		//获得包管理员
		pm = context.getPackageManager();
		//通过包管理员获得包详细信息
		try {
			packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			
			//获得包版本名称
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得版本号
	 */
	public static int getVersionCode(Context context){
		pm = context.getPackageManager();
		try {
			packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
