package tyxo.baseappframe.utils.log;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 日志打印类
 * 关闭log ,只需改 HConfig 里面的 DEBUG 值
 */
public class TLog {

    private static HashMap<String, TILogger> loggerHashMap = new HashMap<>();
    private static final TILogger defaultLogger = new TPrintToLogCatLogger();
    private static final String TAG = "tyxo";
    //private static final String TAG = "lynet";

    public static void addLogger(Context context, TILogger logger) {

        String loggerName = logger.getClass().getName();
        String defaultLoggerName = defaultLogger.getClass().getName();
        if (!loggerHashMap.containsKey(loggerName)
                && !defaultLoggerName.equalsIgnoreCase(loggerName)) {
            logger.open(context);
            loggerHashMap.put(loggerName, logger);
        }
    }

    public static void removeLogger(TILogger logger) {
        String loggerName = logger.getClass().getName();
        if (loggerHashMap.containsKey(loggerName)) {
            logger.close();
            loggerHashMap.remove(loggerName);
        }
    }

    public static void d(Object object, String message) {
        printLoger(TConfig.LOG_DEBUG, object, message);
    }

    public static void e(Object object, String message) {
        printLoger(TConfig.LOG_ERROR, object, message);
    }

    public static void i(Object object, String message) {
        printLoger(TConfig.LOG_INFO, object, message);
    }

    public static void v(Object object, String message) {
        printLoger(TConfig.LOG_VERBOSE, object, message);
    }

    public static void w(Object object, String message) {
        printLoger(TConfig.LOG_WARN, object, message);
    }

    public static void out(Object object, String message) {
        printLoger(TConfig.SYS_OUT, object, message);
    }

    public static void err(Object object, String message) {
        printLoger(TConfig.SYS_ERR, object, message);
    }

    public static void d(String tag, String message) {
        printLoger(TConfig.LOG_DEBUG, tag, message);
    }
    public static void d(String message) {
        printLoger(TConfig.LOG_DEBUG, TAG, message);
    }

    public static void e(String tag, String message) {
        printLoger(TConfig.LOG_ERROR, tag, message);
    }
    public static void e(String message) {
        printLoger(TConfig.LOG_ERROR, TAG, message);
    }

    public static void i(String tag, String message) {
        printLoger(TConfig.LOG_INFO, tag, message);
    }
    public static void i(String message) {
        printLoger(TConfig.LOG_INFO, TAG, message);
    }

    public static void v(String tag, String message) {
        printLoger(TConfig.LOG_VERBOSE, tag, message);
    }
    public static void v(String message) {
        printLoger(TConfig.LOG_VERBOSE, TAG, message);
    }

    public static void w(String tag, String message) {
        printLoger(TConfig.LOG_WARN, tag, message);
    }
    public static void w(String message) {
        printLoger(TConfig.LOG_WARN, TAG, message);
    }

    public static void out(String object, String message) {
        printLoger(TConfig.SYS_OUT, object, message);
    }

    public static void err(String object, String message) {
        printLoger(TConfig.SYS_ERR, object, message);

    }

    public static void println(int priority, String tag, String message) {
        printLoger(priority, tag, message);
    }

    private static void printLoger(int priority, Object object, String message) {
        Class<?> cls = object.getClass();
        String tag = cls.getName();
        String arrays[] = tag.split("\\.");
        tag = arrays[arrays.length - 1];
        printLoger(priority, tag, message);
    }

    private static void printLoger(Context context, int priority, String tag, String message) {
        if (TConfig.DEBUG) {
            printLoger(context, defaultLogger, priority, tag, message);
            Iterator<Entry<String, TILogger>> iter = loggerHashMap.entrySet()
                    .iterator();
            while (iter.hasNext()) {
                Entry<String, TILogger> entry = iter.next();
                TILogger logger = entry.getValue();
                if (logger != null) {
                    printLoger(context, logger, priority, tag, message);
                }
            }
        }
    }

    private static void printLoger(Context context, TILogger logger, int priority, String tag, String message) {

        switch (priority) {
            case TConfig.LOG_VERBOSE:
                logger.v(tag, message, context);
                break;
            case TConfig.LOG_DEBUG:
                logger.d(tag, message, context);
                break;
            case TConfig.LOG_INFO:
                logger.i(tag, message, context);
                break;
            case TConfig.LOG_WARN:
                logger.w(tag, message, context);
                break;
            case TConfig.LOG_ERROR:
                logger.e(tag, message, context);
                break;

            case TConfig.SYS_OUT:
                logger.e(tag, message, context);
                break;

            case TConfig.SYS_ERR:
                logger.e(tag, message, context);
                break;
            default:
                break;
        }
    }

    /**
     * 调试标记
     */
    public static boolean getHardDebugFlag() {
        return TConfig.DEBUG;
    }
}
