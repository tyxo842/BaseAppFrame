package tyxo.baseappframe.utils.phone;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {

    /**
     * 获取进程数
     */
    public static int getProcessCount(Context context) {
        // 获取ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 返回总数
        return am.getRunningAppProcesses().size();
    }

    /**
     * 可用内存
     */
    public static long getMemerySizeAvailable(Context context) {
        // 获取ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // MemoryInfo:内存信息的封装对象 可用内存 总内存
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.availMem;
    }

    /**
     * 总内存数
     */
    // @Deprecated--->因为要求版本很高,所以先不用
    // public static long getMemerySizeTotal(Context context)
    // {
    // // 获取ActivityManager
    // ActivityManager am = (ActivityManager)
    // context.getSystemService(Context.ACTIVITY_SERVICE);
    // //MemoryInfo:内存信息的封装对象 可用内存 总内存
    // ActivityManager.MemoryInfo outInfo=new ActivityManager.MemoryInfo();
    // am.getMemoryInfo(outInfo);
    // return outInfo.totalMem;
    // }
    public static long getMemerySizeTotal(Context context) {
        long total = 0;
        try {
            // ③　 a.打开文件proc/meminfo
            File file = new File("proc/meminfo");
            // 读取
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // BufferedReader:有readLine();
            // b.读取第一行
            String firstLine = reader.readLine();
            // MemTotal: 511128 kB--->第一行的数据,要截取数字即内存大小
            // c.数字提取 d.Kb-->byte->G
            char[] chars = firstLine.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char temp : chars) {
                if ('0' <= temp && temp <= '9') {
                    sb.append(temp);
                }
            }
            //511128-->转换为要求的单位
            String result = sb.toString();
            total = Long.parseLong(result) * 1024;
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 获取进程集合
     */
    public static List<ProcessInfo> findAll(Context context) {
        List<ProcessInfo> list = new ArrayList<ProcessInfo>();
        // 获取ActvityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 创建集合
        List<RunningAppProcessInfo> pinfos = am.getRunningAppProcesses();
        // 已经安装的apk信息的管理者 <application icon lable
        PackageManager pm = context.getPackageManager();
        // RunningAppProcessInfo:进程信息的封装对象 pid 编号
        for (RunningAppProcessInfo item : pinfos) {
            ProcessInfo bean = new ProcessInfo();
            // 进程编号-->???干嘛用 的???
            bean.pid = item.pid;
            // 进程名默认情况 等于包名 已经安装 apk的编号
            bean.processName = item.processName;
            // 内存
            // MemoryInfo内存信息的内存对象
            MemoryInfo[] meminfos = am.getProcessMemoryInfo(new int[]{bean.pid});//这是获取一个内存;之前是获取所有的
            bean.memSize = meminfos[0].getTotalPrivateDirty() * 1024;// kb
            // 图标与应用名
            try {
                ApplicationInfo applicationInfo = pm.getPackageInfo(bean.processName, 0).applicationInfo;
                // 图标
                bean.icon = applicationInfo.loadIcon(pm);
                // 应用名
                bean.name = applicationInfo.loadLabel(pm).toString();

                // flags 是不是系统
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                    bean.isSystem = true;
                } else {
                    bean.isSystem = false;
                }
                list.add(bean);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 杀死进程
     */
    public static void killProcess(Context context, String packageName) {
        // 获取ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(packageName);
    }

    /**
     * 清理进程
     */
    public static void killAllProcess(Context context) {
        // 获取ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        // 获取进程列表
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
        // 逐一清理
        for (RunningAppProcessInfo process : list) {
            if (!context.getPackageName().equals(process.processName)) {
                am.killBackgroundProcesses(process.processName);
            }
        }
        Log.i("tyxo", "清理全部");
    }

    /**
     * 获取用户正在操作的应用程序的包名
     */
    public static String getTaskStackTop(Context context) {
        // 获取ActivityManager管理者
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取任务栈列表
        // RunningTaskInfo：一个java对象封装 任务栈 集合的信息
        // 用户正在操作的程序:最近创建的任务栈,会在集合的最前面
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        //刚刚创建出来的任务栈		//刚要打开的应用程序对应的任务栈
        RunningTaskInfo taskInfo = list.get(0);
        //栈顶元素
        String packageName = taskInfo.topActivity.getPackageName();
        return packageName;
    }

    public static class ProcessInfo {
        public Drawable icon;
        public String name;
        public String processName;
        public int pid;//进程编号
        public long memSize;
        public boolean isSystem;
        public boolean isCheck;
    }
}
