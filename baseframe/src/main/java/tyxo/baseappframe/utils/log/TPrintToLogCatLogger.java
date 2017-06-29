package tyxo.baseappframe.utils.log;

import android.content.Context;
import android.util.Log;

/**
 * 打印日志到LogCat的日志类
 */
public class TPrintToLogCatLogger implements TILogger {
    @Override
    public void d(String tag, String message, Context context) {
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message, Context context) {
        Log.e(tag, message);
    }

    @Override
    public void i(String tag, String message, Context context) {
        Log.i(tag, message);
    }

    @Override
    public void v(String tag, String message, Context context) {
        Log.v(tag, message);
    }

    @Override
    public void w(String tag, String message, Context context) {
        Log.w(tag, message);
    }

    @Override
    public void err(String tag, String message, Context context) {
        System.out.println(tag + " : " + message);
    }

    @Override
    public void out(String tag, String message, Context context) {
        System.err.println(tag + " : " + message);
    }

    @Override
    public void println(int priority, String tag, String message, Context context) {
        Log.println(priority, tag, message);
    }

    @Override
    public void open(Context context) {
    }

    @Override
    public void close() {
    }
}
