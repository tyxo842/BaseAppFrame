package tyxo.baseappframe.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 显示单例的吐司，能连续快速弹的吐司
 */
public class ToastUtil {

    private static Toast toast;

    //因为runOnUiThread是Activity的方法,Context是其父类,没有这个方法,所以定义为final Activity activity
    public static void showToast(final Activity activity, final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*public static void showToastS(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }*/

    public static void show(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastS(Context mContext, String text) {
        if (toast == null) {
            toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);//快速单例
        }
        toast.setText(text);
        toast.show();
    }

    /*public static void showToastS(MyApp myApp, String text, String activityName) {
        if (AndroidUtil.isCurrentActivityTop(myApp, activityName)) {
            if (toast == null) {
                toast = Toast.makeText(myApp, text, Toast.LENGTH_SHORT);
            }
            toast.setText(text);
            toast.show();
        }
    }

    public static void showToastL(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }

    public static void showToastL(MyApp myApp, String text) {
        if (toast == null) {
            toast = Toast.makeText(myApp, text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }*/

    public static boolean isShow = true;

    /*cannot be instantiated*/
    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }
}
