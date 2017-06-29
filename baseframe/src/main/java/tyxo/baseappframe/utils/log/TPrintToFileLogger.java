package tyxo.baseappframe.utils.log;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 打印日志到sdcard的日志类
 */
public class TPrintToFileLogger implements TILogger {

    private String mPath;
    private Writer mWriter;

    private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat(
            "[yyyy-MM-dd HH:mm:ss] ");
    private String basePath = "";
    private static String LOG_DIR = "log";
    private static String BASE_FILENAME = "ta.log";
    private File logDir;

    public TPrintToFileLogger() {
    }

    public void open(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            //logDir = MyApp.getInstance().getExternalCacheDir();
            logDir = context.getExternalCacheDir();
        } else {
            String cacheDir = "/Android/data/"
                    + getPackageName(context)
                    + "/cache/";
            logDir = new File(Environment.getExternalStorageDirectory()
                    .getPath() + cacheDir);
        }
        if (!logDir.exists()) {
            logDir.mkdirs();
            // do not allow media scan
            try {
                new File(logDir, ".nomedia").createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        basePath = logDir.getAbsolutePath() + "/" + BASE_FILENAME;
        try {
            File file = new File(basePath + "-" + getCurrentTimeString());
            mPath = file.getAbsolutePath();
            mWriter = new BufferedWriter(new FileWriter(mPath), 2048);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTimeString() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }

    public String getPath() {
        return mPath;
    }

    @Override
    public void d(String tag, String message, Context context) {
        println(TConfig.LOG_DEBUG, tag, message, context);
    }

    @Override
    public void e(String tag, String message, Context context) {
        println(TConfig.LOG_ERROR, tag, message, context);
    }

    @Override
    public void i(String tag, String message, Context context) {
        println(TConfig.LOG_INFO, tag, message, context);
    }

    @Override
    public void v(String tag, String message, Context context) {
        println(TConfig.LOG_VERBOSE, tag, message, context);
    }

    @Override
    public void w(String tag, String message, Context context) {
        println(TConfig.LOG_WARN, tag, message, context);
    }

    @Override
    public void err(String tag, String message, Context context) {
        println(TConfig.LOG_WARN, tag, message, context);
    }

    @Override
    public void out(String tag, String message, Context context) {
        println(TConfig.LOG_WARN, tag, message, context);
    }

    @Override
    public void println(int priority, String tag, String message, Context context) {
        String printMessage = "";
        switch (priority) {
            case TConfig.LOG_VERBOSE:
                printMessage = "[V]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;
            case TConfig.LOG_DEBUG:
                printMessage = "[D]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;
            case TConfig.LOG_INFO:
                printMessage = "[I]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;
            case TConfig.LOG_WARN:
                printMessage = "[W]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;
            case TConfig.LOG_ERROR:
                printMessage = "[E]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;

            case TConfig.SYS_OUT:
                printMessage = "[OUT]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;

            case TConfig.SYS_ERR:
                printMessage = "[ERR]|"
                        + tag
                        + "|"
                        + getPackageName(context) + "|" + message;
                break;
            default:

                break;
        }
        println(printMessage);
    }

    public void println(String message) {
        try {
            mWriter.write(TIMESTAMP_FMT.format(new Date()));
            mWriter.write(message);
            mWriter.write('\n');
            mWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPackageName(Context context) {
        return context.getPackageName();
    }

    public void close() {
        try {
            mWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
