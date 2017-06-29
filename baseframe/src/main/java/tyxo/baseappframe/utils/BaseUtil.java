package tyxo.baseappframe.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtil {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * 将Json格式的日期字符串转换为Java类型的日期格式
     *
     * @param jsonStr
     * @return
     */
    public static Date JsonDateStringToJavaDate(String jsonStr) {
        try {
            jsonStr = jsonStr.replace("/Date(", "").replace("+0200)/", "");
            if (jsonStr != null) jsonStr = jsonStr.replace("/Date(", "");
            if (jsonStr != null) jsonStr = jsonStr.replace("+0000)/", "");
            if (jsonStr != null) jsonStr = jsonStr.replace("+0800)/", "");
            if (jsonStr != null) jsonStr = jsonStr.replace(")/", "");
            long time = Long.parseLong(jsonStr);
            Date d = new Date(time);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json格式的日期字符串转换为Java类型的日期格式字符串
     *
     * @param jsonStr
     * @return
     */
    public static String JsonDateStringToJavaDateString(String jsonStr) {
        try {
            Date d = JsonDateStringToJavaDate(jsonStr);
            return formatter.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json格式的日期字符串转换为Java类型的日期格式字符串
     *
     * @param jsonDate
     * @return
     */
    public static String getWCFDateFromJsonDate(String jsonDate) {
        String p = "(\\d+)\\+?(\\d+)?"; //"\\/Date(1357904799793+0800)\\/"
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(jsonDate);
        if (matcher.find()) {
            String result = matcher.group(1);

            Date date = new Date(Long.parseLong(result));

            formatter.setTimeZone(TimeZone.getTimeZone("GMT+08"));

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);

            Date localDate = calendar.getTime();

            return formatter.format(localDate);
        } else {
            return "";
        }
    }


    /**
     * 将长日期字符串转换为本地日期
     *
     * @param strDate
     * @return
     */
    public static String conertWCFDateToLocalDate(String strDate) {
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(date);
    }

    /**
     * 将长日期字符串转换为本地日期
     *
     * @param date
     * @return
     */
    public static String conertWCFDateToLocalDate(Date date) {
        return formatter.format(date);
    }

    /**
     * 将长日期字符串转换为本地日期
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    /**
     * MD5单向加密，32位，用于加密密码，因为明文密码在信道中传输不安全，明文保存在本地也不安全
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    public static Date getLocalDate() {
        long currentTimestamp = System.currentTimeMillis(); //获取当前时间

        Date myDate = new Date(currentTimestamp);

        return myDate;
    }

    public static String getLocalDateString() {
        long currentTimestamp = System.currentTimeMillis(); //获取当前时间

        Date myDate = new Date(currentTimestamp);

        String now = formatter.format(myDate);

        return now;
    }

    /**
     * 全角转半角
     *
     * @param input
     * @return
     */
    public static String SBCToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * @param text
     * @return
     */
    public static String toBase64FromString(String text) {
        return Base64.encodeToString(text.getBytes(), Base64.NO_WRAP);
    }

    /**
     * 图片转64位编码字符串
     *
     * @param bitmap
     * @param scale
     * @return
     * @author chenhn
     */
    @SuppressLint("NewApi")
    public static String bitmapToString(Bitmap bitmap, int scale) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bao);
        byte[] ba = bao.toByteArray();
        Log.d("tyxo Time", "oridatalen" + ba.length);
        String ret = Base64.encodeToString(ba,
                Base64.DEFAULT); // 将图片转成base64
        Log.d("tyxo Time", "base64len" + ret.length() * 2);

        return ret == null ? "" : ret;
    }

    /**
     * 格式化日期
     *
     * @param str
     * @return
     */
    public static String dataFormat(String str) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            Date date = format.parse(str);
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 跟当前日期比较
     *
     * @return
     */
    public static int compareDate(Date dt1) {
        try {
            long currentTimestamp = System.currentTimeMillis(); //获取当前时间
            Date dt2_old = new Date(currentTimestamp);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String s = format.format(dt2_old);
            Date dt2 = format.parse(s);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 将字符串转Date
     *
     * @return
     */
    public static Date StringToDate(String dateString) {
        SimpleDateFormat sdf = null;
        if (dateString.length() > 8) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        try {

            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
