package tyxo.baseappframe.utils;

import android.text.TextUtils;

import java.util.Date;

/**
 * 基本功能：条码规则
 */
public class BarCodeRulesUtils {

    /**
     * 根据主次码获取编码规则
     */
    public static String GetInformationFromCode(String vCode, String codeStr, String codeType) {

        String[] DictionaryCode = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "-", ".", " ", "$", "/", "+", "%"};//new Array();//HIBC条码数组

        String[] Ean13Code = {"A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "-", ".", " ", "$", "/", "+", "%"};//检测EAN123条码数组

        char[] charArrForChecked = vCode.toCharArray();
        int totalSum = 0, sumJS = 0, sumOS = 0;

        //"HIBC"
        int temSum = 0;
        int s = 0;
        int br = 0;
        for (int i = 0; i < vCode.length() - 1; i++) {

            if (vCode.charAt(0) != '+' && i == 0) {
                br = 1;
                break;
            }
            for (int j = 0; j < DictionaryCode.length; j++) {
                if (DictionaryCode[j].equals(charArrForChecked[i + 1])) {
                    temSum += j;
                }

            }


        }
        if ((br) != 1) {
            int di = 0;
            for (int i = 0; i < DictionaryCode.length; i++) {
                if (DictionaryCode[i].equals(charArrForChecked[charArrForChecked.length - 1])) {
                    di = (i);
                }
            }
            if ((temSum % 43) == (di)) {
                codeType = "HIBC";
                return codeType;
            }
        }
        //只校验主码
        if (codeStr == "PN") {
            vCode = vCode.trim();

            if (vCode.length() < 8) {
                return codeType = "";
            }

            if (vCode.length() == 16 || vCode.length() == 20) {
                if (vCode.charAt(0) == '0' && vCode.charAt(1) == '1' || vCode.charAt(0) == '0' && vCode.charAt(1) == '2' || vCode.charAt(0) == '0' && vCode.charAt(1) == '0') {
                    vCode = vCode.substring(2, vCode.length());
                }
            }
            charArrForChecked = vCode.toCharArray();

            //GS1---EAN-13-12-14
            for (int i = charArrForChecked.length - 1; i > 0; i--) {

                for (int j = 0; j < Ean13Code.length; j++) {
                    if (Ean13Code[j].equals(charArrForChecked[i]) || j > -1) {
                        break;
                    }
                }
                if (i % 2 == 0) {
                    if (i != 0) {
                        sumJS += parseInt(String.valueOf(charArrForChecked[i - 1]));
                    } else {
                        sumJS += parseInt(String.valueOf(charArrForChecked[i]));
                    }
                } else {
                    if (i != 0) {
                        sumOS += parseInt(String.valueOf(charArrForChecked[i - 1]));
                    } else {
                        sumOS += parseInt(String.valueOf(charArrForChecked[i]));
                    }
                }
            }
            if (charArrForChecked.length % 2 == 0) {

                totalSum = sumJS + sumOS * 3;

            } else {
                totalSum = sumJS * 3 + sumOS;
            }

            int num = ((totalSum / 10)) + (totalSum % 10 > 0 ? 1 : 0);
            //取得条码最后一位
            int dd = parseInt(String.valueOf(charArrForChecked[vCode.length() - 1]));

            if (num * 10 - totalSum == dd) {
                codeType = "EAN";
                return codeType;
            }

        }
        return codeType = "";
    }

    private static int parseInt(String s) {
        return Integer.parseInt(s);
    }


    /**
     * 从批次码中挑选信息   生产批号   灭菌批号
     */
    public static String GetInfoFromRange(String code, String range) {
        if (!TextUtils.isEmpty(range)) {
            String[] Ranges = range.split(",");
            try {
                return code.substring(parseInt(Ranges[0]) - 1, parseInt(Ranges[1]) + parseInt(Ranges[0]));

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * //时间类型字段-截取
     *
     * @return
     */
    public static String GetDateInfoFromRange(String Code, String Range) {
        if (!TextUtils.isEmpty(Range)) {
            String[] Ranges = Range.split(",");
            String dateType = Range.split("\\|")[1];
            try {
                return GetDateInformation(Code.substring(parseInt(Ranges[0]) - 1, parseInt(Ranges[1].split("\\|")[0]) + parseInt(Ranges[0])), dateType);

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();

            }
        }
        return "";
    }

    //根据日期格式格式化日期为要显示日期
    //dateString--要格式的日期字条串 如：YYMMDD=>>150928  YYJJJ=>>15182   MMYY==> >1115
    //dateType"--格式：如YYMMDD
    public static String GetDateInformation(String dateString, String dateType) {
        Date date = new Date(System.currentTimeMillis());
        //取当前时间的前两位 如：2015-----20
        //System.out.println("ye = "+date.getYear());
        String ye = String.valueOf(date.getYear() + 1900).substring(0, 2);
        String result = "";
        switch (dateType) {
            case "YYMMDD": { //YYMMDD=>>150928    =>return  20150928
                result = ye + dateString.substring(0, 2) + "-" + dateString.substring(2, 4) + "-" + dateString.substring(4, 6);
            }
            break;
            case "YYJJJ": {// YYJJJ=>>15182
                int flag = 0;
                int year = Integer.valueOf(ye + dateString.substring(0, 2));

                int monthIndex = 0;
                int mouth = 0;
                int day = Integer.parseInt(dateString.substring(2, 5));
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 100)) flag = 1;
                else flag = 0;
                //月份在一年中对应 天数
                int[] days = {0, 31, 59 + flag, 90 + flag, 120 + flag, 151 + flag, 181 + flag, 212 + flag, 243 + flag, 273 + flag, 304 + flag, 334 + flag, 365 + flag};
                for (int i = 0; i < days.length; i++) {
                    if (day <= days[i]) {
                        mouth = i;
                        monthIndex = i - 1;
                        break;
                    }
                }
                day = day - days[monthIndex];

                result = year + "-" + (mouth < 10 ? "0" + mouth : mouth) + "-" + (day < 10 ? "0" + day : day);
            }
            break;

            case "MMYY": {
                String year = ye + dateString.substring(2, 4);
                result = ye + dateString.substring(2, 4) + "-" + dateString.substring(0, 2);

            }
            break;
            case "YYMM": {
                result = ye + dateString.substring(0, 2) + "-" + dateString.substring(2, 4);
            }
            break;
            case "MMYYYY": {
                result = dateString.substring(2, 6) + "-" + dateString.substring(0, 2);
            }
            break;
            default: {
            }
        }
        return result;
    }
}
