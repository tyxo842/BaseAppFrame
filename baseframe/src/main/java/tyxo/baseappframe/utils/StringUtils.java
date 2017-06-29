package tyxo.baseappframe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 获取手机信息
     */
    public static String getPhoneInfos(String str) {
        String deviceType = android.os.Build.MODEL; // 手机型号 ATH-UL00
        String systemVersion = "Android " + android.os.Build.VERSION.RELEASE;//Android 5.1.1
        String VERSION_SDK = android.os.Build.VERSION.SDK; // 22
        String MANUFACTURER = android.os.Build.MANUFACTURER; // 手机厂商 HUAWEI

        Log.i("tyxo", "deviceType: " + deviceType + ", systemVersion: " + systemVersion +
                ", VERSION_SDK: " + VERSION_SDK + ", MANUFACTURER: " + MANUFACTURER);

        if (str.equals(deviceType)) {
            return deviceType;
        } else if (str.equals(systemVersion)) {
            return systemVersion;
        } else if (str.equals(VERSION_SDK)) {
            return VERSION_SDK;
        } else if (str.equals(MANUFACTURER)) {
            return MANUFACTURER;
        } else {
            return "deviceType: " + deviceType + ", systemVersion: " + systemVersion +
                    ", VERSION_SDK: " + VERSION_SDK + ", MANUFACTURER: " + MANUFACTURER;
        }
    }

    // 获取SdCard缓存路径
    public static String SDCardCachePath = Environment
            .getExternalStorageDirectory() + "/HengZhao/";

    public static boolean isEditTextEmpty(EditText editText) {
        String str = editText.getText().toString();
        return str == null || str.length() == 0;
    }

    public static String getEditTextString(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * 字符串去除头尾空格
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。
     *
     * @param subject 被查找字符串。
     * @param search  要查找的子字符串。
     * @return 指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。
     */
    public static int ignoreCaseIndexOf(String subject, String search) {
        return ignoreCaseIndexOf(subject, search, -1);
    }

    /**
     * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。
     *
     * @param subject   被查找字符串。
     * @param search    要查找的子字符串。
     * @param fromIndex 开始查找的索引位置。其值没有限制，如果它为负，则与它为 0 的效果同样：将查找整个字符串。
     *                  如果它大于此字符串的长度，则与它等于此字符串长度的效果相同：返回 -1。
     * @return 指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。
     */
    public static int ignoreCaseIndexOf(String subject, String search,
                                        int fromIndex) {

        // 当被查找字符串或查找子字符串为空时，抛出空指针异常。
        if (subject == null || search == null) {
            throw new NullPointerException("输入的参数为空");
        }

        fromIndex = fromIndex < 0 ? 0 : fromIndex;

        if (search.equals("")) {
            return fromIndex >= subject.length() ? subject.length() : fromIndex;
        }

        int index1 = fromIndex;
        int index2 = 0;

        char c1;
        char c2;

        loop1:
        while (true) {

            if (index1 < subject.length()) {
                c1 = subject.charAt(index1);
                c2 = search.charAt(index2);

            } else {
                break loop1;
            }

            while (true) {
                if (isEqual(c1, c2)) {

                    if (index1 < subject.length() - 1
                            && index2 < search.length() - 1) {

                        c1 = subject.charAt(++index1);
                        c2 = search.charAt(++index2);
                    } else if (index2 == search.length() - 1) {

                        return fromIndex;
                    } else {

                        break loop1;
                    }

                } else {

                    index2 = 0;
                    break;
                }
            }
            // 重新查找子字符串的位置
            index1 = ++fromIndex;
        }

        return -1;
    }

    /**
     * 判断两个字符是否相等。
     *
     * @param c1 字符1
     * @param c2 字符2
     * @return 若是英文字母，不区分大小写，相等true，不等返回false； 若不是则区分，相等返回true，不等返回false。
     */
    private static boolean isEqual(char c1, char c2) {
        // 字母小写 字母大写
        if (((97 <= c1 && c1 <= 122) || (65 <= c1 && c1 <= 90))
                && ((97 <= c2 && c2 <= 122) || (65 <= c2 && c2 <= 90))
                && ((c1 - c2 == 32) || (c2 - c1 == 32))) {

            return true;
        } else if (c1 == c2) {
            return true;
        }

        return false;
    }

    public static SimpleDateFormat formatD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    // 获得年月日时间
    public static String getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        // 获取当前时间
        String str = format.format(date);
        return str;
    }

    // 获得含时分秒的时间
    public static String getCurrentDateDetail() {
        Date date = new Date(System.currentTimeMillis());
        // 获取当前时间
        String str = formatD.format(date);
        return str;
    }

    // 获取当前时间往后一天
    public static String getCurrentDateDay1() {
        long day1 = 24 * 60 * 60 * 1000;
        Date date = new Date(System.currentTimeMillis() + day1);
        // 获取当前时间
        String str = format.format(date);
        return str;
    }

    public static String getCurrentDate(long timeMillis) {
        if (timeMillis == 0L)
            return null;
        Date date = new Date(timeMillis);
        // 获取当前时间
        String str = format.format(date);
        return str;
    }

    public static String getCurrentDate(String timeMillis) {
        if (isEmpty(timeMillis))
            return new String("");
        Date date = new Date(Long.parseLong(timeMillis));
        // 获取当前时间
        String str = format.format(date);
        return str;
    }

    public static String getCustomDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        // 获取当前时间
        String str = sdf.format(date);
        return str;
    }

    // 获取前一个月的时间
    public static String getMonthDate() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, -1);
        Date lastMonth = ca.getTime();
        String str = format.format(lastMonth);
        return str;
    }

    /**
     * 判断日期是否符合规则,1为生产日期,2为失效日期:生产不能晚于当前,失效不能早于当前
     */
    public static String dteMatchRule(String date, int nu) {
        switch (nu) {
            case 1:
                if (date != null && date != "" && date.length() == 7) {
                    date = date + "-01";
                } else {
                }
                return date;
            case 2:
                if (date != null && date != "" && date.length() == 7) {
                    String strs[] = date.split("-");
                    int year = Integer.valueOf(strs[0]);
                    int mon = Integer.valueOf(strs[1]);
                    date = getMonMaxDay(year, mon);
                } else {
                }
                return date;
        }
        return date;
    }

    /**
     * 获取 传入月 最后一天
     */
    public static String getMonMaxDay(int year, int mon) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, mon);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        return format.format(lastDate);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证电话格式
     * userTelephone
     * "^(010|021|022|023|024|025|026|027|028|029|852)\\d{8}$"
     */
    public static boolean isTelNO(String mobiles) {

        String telRegex1 = "^(010|021|022|023|024|025|026|027|028|029|852)\\d{8}$";
        String telRegex2 = "^(0[3-9][0-9]{2})\\d{7,8}$";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex1) || mobiles.matches(telRegex2);
    }

    /**
     * 验证数字^[0-9]*$
     */
    public static boolean isNO(String number) {

        String telRegex = "[0-9]*";
        if (TextUtils.isEmpty(number))
            return false;
        else
            return number.matches(telRegex);
    }

    /**
     * 验证小数^[0-9]{1,10}(\\.[0-9]{0,2})?$
     */
    public static boolean isDouble(String number) {

        String telRegex = "[0-9]{1,10}(\\.[0-9]{0,2})?";
        if (TextUtils.isEmpty(number))
            return false;
        else
            return number.matches(telRegex);
    }

    /**
     * 获取订单号 <a href=/S_Order/SupplierOrderItemListEdit?orderId=
     * plat_ent_yd0000000000316>点击查看：plat_ent_yd0000000000316</a>
     *
     * @return
     */
    public static String getOrderId(String content) {

        String result = "";

        String regEx_id = "<a.*?orderId=(.*?)>"; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_id, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(content);
        int i = 0;
        while (m_image.find()) {
            result = m_image.group(1);
            Log.v("tyxo", "result = " + result + " i = " + i++);
        }
        return result;
    }

    /**
     * 拼接WCF服务地址
     */
    public static String combineUrl(String baseUrl, String relativeUrl) {

        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        if (relativeUrl.startsWith("1")) {
            relativeUrl = relativeUrl.substring(1);
        }
        return String.format("%s/%s", baseUrl, relativeUrl);
    }

    /**
     * 拼接WCF服务地址
     *
     * @param context
     * @param resId   表示WCF服务接口的资源id
     * @return
     */
    public static String combineUrl(Context context, String baseUrl, int resId) {
        String relativeUrl = context.getResources().getString(resId);
        return combineUrl(baseUrl, relativeUrl);
    }

    public static String combineURl(String baseUrl, String relativeUrl) {

        return String.format("%s%s", baseUrl, relativeUrl);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String stringFilter(String text, String regEx)
            throws PatternSyntaxException {

        // String regEx = "[^a-zA-Z0-9]";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        return m.replaceAll("").trim();
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 比较俩个日期大小
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare_date(String DATEFROM, String DATETO) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATEFROM);
            Date dt2 = df.parse(DATETO);
            if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2后");
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    /**
     * 判空字符串
     *
     * @param str 字符串
     * @return 如果str不为null，返回str，否则返回 "";
     */
    public static String emptyStr(String str) {
        return str != null ? str : "";
    }


    /**
     * 过滤字符串,只允许字母,数字和汉字
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String isNullString(String string) {
        if (string == null || string.equals("null")) {
            string = "";
        }
        return string;
    }

    /**
     * 字符串转换成时间
     */
    public static Date stringToDate(String string) {
		/*string = string.substring(0, string.indexOf(" "));
		String[] split = string.split("/");
		if (split.length >= 3) {
			return new Date(Integer.parseInt(split[0])-1900, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
		} else {
			return null;
		}*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(String string) {
        Date date = stringToDate(string);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    // // 判断设置的失效日期是否大于当前日期
    public static boolean isDateValueable(String setDate) {
        boolean isValueable = false;
        String currentDate = StringUtils.getCurrentDate();
        String[] strsCur = currentDate.split("-");
        String[] strsSet = setDate.split("-");
        int yearCur = Integer.valueOf(strsCur[0]);
        int yearSet = Integer.valueOf(strsSet[0]);
        int monCur = Integer.valueOf(strsCur[1]);
        int monSet = Integer.valueOf(strsSet[1]);
        int dayCur = Integer.valueOf(strsCur[2]);
        int daySet = Integer.valueOf(strsSet[2]);
        boolean isYear = yearSet > yearCur;
        boolean isMon = (!(yearSet < yearCur)) && (monSet > monCur);
        boolean isDay = (!(yearSet < yearCur)) && (!(monSet < monCur)) && (daySet > dayCur);
//		HLog.v("lytime","当前月: "+monCur+"; 当前日: "+dayCur+"; 设置月:" +monSet+"; 设置日: "+daySet);
        if (isYear || isMon || isDay) {
            isValueable = true;
        } else {
            isValueable = false;
        }
//		HLog.v("lytime","isMon: "+isMon+"; isDay: "+isDay+"; isValueable: "+isValueable);
        return isValueable;
    }

    // 判断日期是否是YY mm dd
    public static String getDateFromate(String indate) {
        Log.v("lytime", "传入的时间: " + indate);
        String[] strsSet = indate.split("-");
        int yearSet = Integer.valueOf(strsSet[0]);
        int monSet = Integer.valueOf(strsSet[1]);
        int daySet = Integer.valueOf(strsSet[2]);
        String mon = "";
        String day = "";
        if (monSet > 9) {
            mon = "" + monSet;
        } else {
            mon = "0" + monSet;
//			indate = indate.replace(monSet+"",mon); // 会替代年中数字 如2016-6--->20106-06
//			HLog.v("lytime","monSet: "+monSet+"; mon : "+mon+"; indate: "+indate);
        }
        if (daySet > 9) {
            day = "" + daySet;
        } else {
            day = "0" + daySet;
//			indate = indate.replace(daySet+"",day);
//			HLog.v("lytime","daySet: "+daySet+"; day : "+day+"; indate: "+indate);
        }
        indate = "" + yearSet + "-" + mon + "-" + day;
        Log.v("lytime", "传出的时间: " + indate);
        return indate;
    }

    // 2016/6/6 0:00:00  ---> 2016-06-06
    public static String getYMDDataFromYMDSMS(String oldDate) {
        if (null != oldDate) {
            String oldD = oldDate.replaceAll("/", "-").substring(0, oldDate.indexOf(" "));
            return getDateFromate(oldD);
        } else {
            return "";
        }
    }

    // 控制输入数字范围  //若超了Integer范围,valueOf会报错
    public static boolean switchInputNum(Context context, String num) {
        //if (num.matches("[1-9]{1}[0-9]{0,9}")) {//正整数,首位非0
        if (num.matches("[0-9]{0,9}")) {
            return true;
        } else {
            Toast.makeText(context, "配送数量超出范围", 0).show();
            return false;
        }
    }

    // 格式化时间
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    // 收起键盘
    public static void clearFocuse(Context context, EditText... ets) {
//        et_o_add_produce_num.clearFocus();
//        et_o_add_sterilisation_num.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//有则隐,无则显
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            for (EditText et : ets) {
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            }
        }
    }

    /**
     * 判定输入汉字
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     */
    public boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * is null or its length is 0 or it is made by space
     * <p>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str str
     * @return if string is null or its size is 0 or it is made by space, return
     * true, else return false.
     */
    public static boolean isBlank(String str) {

        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str str
     * @return if string is null or its size is 0, return true, else return
     * false.
     */
    public static boolean isEmpty(CharSequence str) {

        return (str == null || str.length() == 0);
    }

    /**
     * get length of CharSequence
     * <p>
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param str str
     * @return if str is null or empty, return 0, else return {@link
     * CharSequence#length()}.
     */
    public static int length(CharSequence str) {

        return str == null ? 0 : str.length();
    }

    /**
     * null Object to empty string
     * <p>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str str
     * @return String
     */
    public static String nullStrToEmpty(Object str) {

        return (str == null
                ? ""
                : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * @param str str
     * @return String
     */
    public static String capitalizeFirstLetter(String str) {

        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c))
                ? str
                : new StringBuilder(str.length()).append(
                Character.toUpperCase(c))
                .append(str.substring(1))
                .toString();
    }

    /**
     * encoded in utf-8
     *
     * @param str 字符串
     * @return 返回一个utf8的字符串
     */
    public static String utf8Encode(String str) {

        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * @param href 字符串
     * @return 返回一个html
     */
    public static String getHrefInnerHtml(String href) {

        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg,
                Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * @param source 字符串
     * @return 返回htmL到字符串
     */
    public static String htmlEscapeCharsToString(String source) {

        return isEmpty(source)
                ? source
                : source.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"");
    }

    /**
     * @param s str
     * @return String
     */
    public static String fullWidthToHalfWidth(String s) {

        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * @param s 字符串
     * @return 返回的数值
     */
    public static String halfWidthToFullWidth(String s) {

        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * @param str 资源
     * @return 特殊字符串切换
     */

    public static String replaceBlanktihuan(String str) {

        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断给定的字符串是否为null或者是空的
     */
    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }

    /**
     * 判断给定的字符串是否不为null且不为空
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 判断给定的字符串数组中的所有字符串是否都为null或者是空的
     *
     * @param strings 给定的字符串
     */
    public static boolean isEmpty(String... strings) {
        boolean result = true;
        for (String string : strings) {
            if (isNotEmpty(string)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断给定的字符串数组中是否全部都不为null且不为空
     *
     * @param strings 给定的字符串数组
     * @return 是否全部都不为null且不为空
     */
    public static boolean isNotEmpty(String... strings) {
        boolean result = true;
        for (String string : strings) {
            if (isEmpty(string)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 如果字符串是null或者空就返回""
     */
    public static String filterEmpty(String string) {
        return isNotEmpty(string) ? string : "";
    }

    /**
     * 在给定的字符串中，用新的字符替换所有旧的字符
     *
     * @param string  给定的字符串
     * @param oldchar 旧的字符
     * @param newchar 新的字符
     * @return 替换后的字符串
     */
    public static String replace(String string, char oldchar, char newchar) {
        char chars[] = string.toCharArray();
        for (int w = 0; w < chars.length; w++) {
            if (chars[w] == oldchar) {
                chars[w] = newchar;
                break;
            }
        }
        return new String(chars);
    }

    /**
     * 把给定的字符串用给定的字符分割
     *
     * @param string 给定的字符串
     * @param ch     给定的字符
     * @return 分割后的字符串数组
     */
    public static String[] split(String string, char ch) {
        ArrayList<String> stringList = new ArrayList<String>();
        char chars[] = string.toCharArray();
        int nextStart = 0;
        for (int w = 0; w < chars.length; w++) {
            if (ch == chars[w]) {
                stringList.add(new String(chars, nextStart, w - nextStart));
                nextStart = w + 1;
                if (nextStart ==
                        chars.length) {    //当最后一位是分割符的话，就再添加一个空的字符串到分割数组中去
                    stringList.add("");
                }
            }
        }
        if (nextStart <
                chars.length) {    //如果最后一位不是分隔符的话，就将最后一个分割符到最后一个字符中间的左右字符串作为一个字符串添加到分割数组中去
            stringList.add(new String(chars, nextStart,
                    chars.length - 1 - nextStart + 1));
        }
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * 计算给定的字符串的长度，计算规则是：一个汉字的长度为2，一个字符的长度为1
     *
     * @param string 给定的字符串
     * @return 长度
     */
    public static int countLength(String string) {
        int length = 0;
        char[] chars = string.toCharArray();
        for (int w = 0; w < string.length(); w++) {
            char ch = chars[w];
            if (ch >= '\u0391' && ch <= '\uFFE5') {
                length++;
                length++;
            } else {
                length++;
            }
        }
        return length;
    }

    private static char[] getChars(char[] chars, int startIndex) {
        int endIndex = startIndex + 1;
        //如果第一个是数字
        if (Character.isDigit(chars[startIndex])) {
            //如果下一个是数字
            while (endIndex < chars.length &&
                    Character.isDigit(chars[endIndex])) {
                endIndex++;
            }
        }
        char[] resultChars = new char[endIndex - startIndex];
        System.arraycopy(chars, startIndex, resultChars, 0, resultChars.length);
        return resultChars;
    }

    /**
     * 是否全是数字
     */
    public static boolean isAllDigital(char[] chars) {
        boolean result = true;
        for (int w = 0; w < chars.length; w++) {
            if (!Character.isDigit(chars[w])) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 删除给定字符串中所有的旧的字符
     *
     * @param string 源字符串
     * @param ch     要删除的字符
     * @return 删除后的字符串
     */
    public static String removeChar(String string, char ch) {
        StringBuffer sb = new StringBuffer();
        for (char cha : string.toCharArray()) {
            if (cha != '-') {
                sb.append(cha);
            }
        }
        return sb.toString();
    }

    /**
     * 删除给定字符串中给定位置处的字符
     *
     * @param string 给定字符串
     * @param index  给定位置
     */
    public static String removeChar(String string, int index) {
        String result = null;
        char[] chars = string.toCharArray();
        if (index == 0) {
            result = new String(chars, 1, chars.length - 1);
        } else if (index == chars.length - 1) {
            result = new String(chars, 0, chars.length - 1);
        } else {
            result = new String(chars, 0, index) +
                    new String(chars, index + 1, chars.length - index);
            ;
        }
        return result;
    }

    /**
     * 删除给定字符串中给定位置处的字符
     *
     * @param string 给定字符串
     * @param index  给定位置
     * @param ch     如果同给定位置处的字符相同，则将给定位置处的字符删除
     */
    public static String removeChar(String string, int index, char ch) {
        String result = null;
        char[] chars = string.toCharArray();
        if (chars.length > 0 && chars[index] == ch) {
            if (index == 0) {
                result = new String(chars, 1, chars.length - 1);
            } else if (index == chars.length - 1) {
                result = new String(chars, 0, chars.length - 1);
            } else {
                result = new String(chars, 0, index) +
                        new String(chars, index + 1, chars.length - index);
                ;
            }
        } else {
            result = string;
        }
        return result;
    }

    /**
     * 对给定的字符串进行空白过滤
     *
     * @param string 给定的字符串
     * @return 如果给定的字符串是一个空白字符串，那么返回null；否则返回本身。
     */
    public static String filterBlank(String string) {
        if ("".equals(string)) {
            return null;
        } else {
            return string;
        }
    }

    /**
     * 将给定字符串中给定的区域的字符转换成小写
     *
     * @param str        给定字符串中
     * @param beginIndex 开始索引（包括）
     * @param endIndex   结束索引（不包括）
     * @return 新的字符串
     */
    public static String toLowerCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex),
                str.substring(beginIndex, endIndex)
                        .toLowerCase(Locale.getDefault()));
    }

    /**
     * 将给定字符串中给定的区域的字符转换成大写
     *
     * @param str        给定字符串中
     * @param beginIndex 开始索引（包括）
     * @param endIndex   结束索引（不包括）
     * @return 新的字符串
     */
    public static String toUpperCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex),
                str.substring(beginIndex, endIndex)
                        .toUpperCase(Locale.getDefault()));
    }

    /**
     * 将给定字符串的首字母转为小写
     *
     * @param str 给定字符串
     * @return 新的字符串
     */
    public static String firstLetterToLowerCase(String str) {
        return toLowerCase(str, 0, 1);
    }

    /**
     * 将给定字符串的首字母转为大写
     *
     * @param str 给定字符串
     * @return 新的字符串
     */
    public static String firstLetterToUpperCase(String str) {
        return toUpperCase(str, 0, 1);
    }

    /**
     * 将给定的字符串MD5加密
     *
     * @param string 给定的字符串
     * @return MD5加密后生成的字符串
     */
    public static String MD5(String string) {
        String result = null;
        try {
            char[] charArray = string.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }

            StringBuffer hexValue = new StringBuffer();
            byte[] md5Bytes = MessageDigest.getInstance("MD5")
                    .digest(byteArray);
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }

            result = hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断给定的字符串是否以一个特定的字符串开头，忽略大小写
     *
     * @param sourceString 给定的字符串
     * @param newString    一个特定的字符串
     */
    public static boolean startsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        } else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(0, newLength, newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        } else {
            return false;
        }
    }

    /**
     * 判断给定的字符串是否以一个特定的字符串结尾，忽略大小写
     *
     * @param sourceString 给定的字符串
     * @param newString    一个特定的字符串
     */
    public static boolean endsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        } else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(sourceLength - newLength, sourceLength,
                    newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        } else {
            return false;
        }
    }

    /**
     * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上appendString
     */
    public static String checkLength(String string, int maxLength, String appendString) {
        if (string.length() > maxLength) {
            string = string.substring(0, maxLength);
            if (appendString != null) {
                string += appendString;
            }
        }
        return string;
    }

    /**
     * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上…
     */
    public static String checkLength(String string, int maxLength) {
        return checkLength(string, maxLength, "…");
    }
}