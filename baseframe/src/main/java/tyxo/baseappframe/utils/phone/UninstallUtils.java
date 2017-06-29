package tyxo.baseappframe.utils.phone;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import tyxo.baseappframe.utils.log.TLog;

public class UninstallUtils {

    public static void uninstall(Context context, AppInfo info) {
        // b.调用卸载Activity
        // c.监听广播 调用刷新界面
        if (!info.isSystem) {
            // a.创建 Intent 包含的请求参数
            // <intent-filter>
            // <action android:name="android.intent.action.VIEW" />
            // <action android:name="android.intent.action.DELETE" />
            // <category android:name="android.intent.category.DEFAULT" />
            // <data android:scheme="package" />
            // </intent-filter>
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DELETE");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + info.packageName));
            context.startActivity(intent);

        } else {// 系统应用
            try {        // root
                /**用到 RootTools.jar 不合适打成工具类jar包 --> 注释掉*/
                /*if (!RootTools.isRootAvailable()) {
					ToastUtil.show(context, "当前未root不能进行系统应用的卸载 ！");
					return ;
				}
				if (!RootTools.isAccessGiven()) {
					ToastUtil.show(context, "当前未授权不能进行系统应用的卸载 ！");
					return ;
				}
				//发送root命令,和卸载命令
				RootTools.sendShell("mount -remount  , rw /system/app", 3000);
				RootTools.sendShell("rm -r " + info.path, 3000);*/
            } catch (Exception e) {
                e.printStackTrace();
                TLog.i("命令出错!");
            }
        }
    }

    public class AppInfo {

        public Drawable icon;
        public String appname;
        public String path;
        public long appsize;
        public boolean isSd;
        public boolean isSystem;
        public String packageName;//id

        public long cacheSize;
        public int uid;
        //流量
        public long mobileTotal;
        public long mobileSnd;
        public long mobileRec;

    }
}
