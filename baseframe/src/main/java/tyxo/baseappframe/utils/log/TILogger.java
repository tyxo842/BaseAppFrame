package tyxo.baseappframe.utils.log;

import android.content.Context;

/**
 * 日志打印类
 */
public interface TILogger {
    void v(String tag, String message, Context context);

    void d(String tag, String message, Context context);

    void i(String tag, String message, Context context);

    void w(String tag, String message, Context context);

    void e(String tag, String message, Context context);

    void out(String tag, String message, Context context);

    void err(String tag, String message, Context context);

    //void open();

    void open(Context context);

    void close();

    void println(int priority, String tag, String message, Context context);
}
