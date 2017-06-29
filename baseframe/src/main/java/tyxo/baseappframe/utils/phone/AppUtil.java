package tyxo.baseappframe.utils.phone;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * app相关辅助类
 */
public class AppUtil {

    private AppUtil() { 
        /* cannot be instantiated*/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 读取所有已经安装的apk信息 data/app system/app
     */
    public static List<AppInfo> findAllApp(Context context){
        List<AppInfo> list = new ArrayList<AppInfo>();
        // a.PackageManager 包管理者
        PackageManager pm = context.getPackageManager();
        // b.PackageInfo 包信息 功能清单的全部标签信息
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        //	 |-->flags:Additional option flags:附加的可选标识,这样的flag一般直接填0就可以
        for (PackageInfo p : packageInfos) {
            //uid : user id;
            AppInfo info = new AppInfo();
            // 包名
            info.packageName = p.packageName;
            // c.ApplicationInfo application信息
            ApplicationInfo application = p.applicationInfo;// get效率比直接访问属性低
            // 图标
            info.icon = application.loadIcon(pm);
            // 应用名称
            info.appname = application.loadLabel(pm).toString();
            //apk 安装 的路径   data/app/xxx00.apk
            info.uid = application.uid;	//用户编号
            info.mobileSnd = TrafficStats.getUidTxBytes(info.uid) == -1?0:TrafficStats.getUidTxBytes(info.uid);//应用发送
            info.mobileRec = TrafficStats.getUidRxBytes(info.uid) == -1?0:TrafficStats.getUidRxBytes(info.uid);//应用接收
            info.mobileTotal = info.mobileSnd + info.mobileRec;	//总流量=发送(上传)+接收(下载)

            info.path = application.sourceDir;
            File appFile = new File(info.path);
            info.appsize = appFile.length();
            // 应用类型
            // ApplicationInfo.FLAG_SYSTEM system/app
            if ((application.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                info.isSystem = true;
            } else {
                info.isSystem = false;
            }
            //安装路径
            if ((application.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
                info.isSd=true;
            } else {
                info.isSd = false;
            }
            list.add(info);
        }
        // d.分类
        return list;
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {

        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序的版本Code信息
     *
     * @param context
     * @return 版本code
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static class AppInfo {

        public Drawable icon;
        public String appname;
        public String path;
        public long appsize;
        public boolean isSd;
        public boolean isSystem;
        public String packageName;// id
        public long cacheSize;
        public int uid;
        //流量
        public long mobileTotal;
        public long mobileSnd;
        public long mobileRec;
    }
}