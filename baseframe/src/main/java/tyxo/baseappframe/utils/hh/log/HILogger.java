package tyxo.baseappframe.utils.hh.log;

/**
 * @Description HILogger：日志打印类
 * @ClassName: HILogger
 * @date 2015-04-29 15：17
 */
public interface HILogger {
    void v(String tag, String message);

    void d(String tag, String message);

    void i(String tag, String message);

    void w(String tag, String message);

    void e(String tag, String message);

    void out(String tag, String message);

    void err(String tag, String message);

    void open();

    void close();

    void println(int priority, String tag, String message);
}
