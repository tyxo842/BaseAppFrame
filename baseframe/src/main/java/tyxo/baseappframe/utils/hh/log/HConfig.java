
package tyxo.baseappframe.utils.hh.log;

/**
 * @Description HLoggerConfig：日志调试设置类
 * @ClassName: HLoggerConfig
 * @date 2015-04-29 15：17
 */
public class HConfig {

    public static final boolean DEBUG = true;

    /**
     * Priority constant for the println method; use TALogger.v.
     */
    public static final int LOG_VERBOSE = 2;

    /**
     * Priority constant for the println method; use TALogger.d.
     */
    public static final int LOG_DEBUG = 3;

    /**
     * Priority constant for the println method; use TALogger.i.
     */
    public static final int LOG_INFO = 4;

    /**
     * Priority constant for the println method; use TALogger.w.
     */
    public static final int LOG_WARN = 5;

    /**
     * Priority constant for the println method; use TALogger.e.
     */
    public static final int LOG_ERROR = 6;
    /**
     * Priority constant for the println method.
     */
    public static final int LOG_ASSERT = 7;
    /**
     * System.out.println();
     */
    public static final int SYS_OUT = 8;
    /**
     * System.err.println();
     */
    public static final int SYS_ERR = 9;
}
