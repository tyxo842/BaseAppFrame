package tyxo.baseappframe.utils;

/**
 * 基本功能：扫码解码,调用 javascript .
 * 创建时间：2016/5/9 14:06:06
 * description : 依赖 org.mozilla:rhino:1.7.7
 */
public class JsCodeUtils {
    /*
    public static String GetInformationFromCode(Object[] params) {
        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        // Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();
            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            rhino.evaluateString(scope, "function GetInformationFromCode(vCode, codeStr, codeType) {\n" +
                    "\n" +
                    "    var DictionaryCode = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9',\n" +
                    "        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',\n" +
                    "        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',\n" +
                    "        '-', '.', ' ', '$', '/', '+', '%'];//new Array();//HIBC条码数组\n" +
                    "\n" +
                    "    var Ean13Code = ['A', 'B', 'C', 'D', 'E', 'F', 'G',\n" +
                    "        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',\n" +
                    "        '-', '.', ' ', '$', '/', '+', '%'];//检测EAN123条码数组\n" +
                    "\n" +
                    "    var charArrForChecked = vCode.split(\"\");\n" +
                    "    var totalSum = 0; sumJS = 0, sumOS = 0;\n" +
                    "\n" +
                    "    //\"HIBC\"\n" +
                    "    var temSum = 0;\n" +
                    "    var s = 0;\n" +
                    "    var br = 0;\n" +
                    "    for (var i = 0; i < charArrForChecked.length - 1; i++) {\n" +
                    "\n" +
                    "        if (charArrForChecked[0] != \"+\" && i == 0) {\n" +
                    "            br = 1;\n" +
                    "            break;\n" +
                    "        }\n" +
                    "\n" +
                    "        s = DictionaryCode.indexOf(charArrForChecked[i])\n" +
                    "        temSum += parseInt(s);\n" +
                    "    }\n" +
                    "    if (parseInt(br) != 1) {\n" +
                    "        var di = parseInt(DictionaryCode.indexOf(charArrForChecked[charArrForChecked.length - 1]));\n" +
                    "\n" +
                    "        if ((temSum % 43) == parseInt(di)) {\n" +
                    "            codeType = \"HIBC\";\n" +
                    "            return codeType;\n" +
                    "        }\n" +
                    "    }\n" +
                    "    //只校验主码\n" +
                    "    if (codeStr == \"PN\") {\n" +
                    "        vCode = vCode.trim();\n" +
                    "\n" +
                    "        if (vCode.length < 8) {\n" +
                    "            return codeType = \"\";\n" +
                    "        }\n" +
                    "\n" +
                    "        if (vCode.length == 16 || vCode.length == 20) {\n" +
                    "            if (vCode[0] == \"0\" && vCode[1] == \"1\" || vCode[0] == \"0\" && vCode[1] == \"2\" || vCode[0] == \"0\" && vCode[1] == \"0\") {\n" +
                    "                vCode = vCode.substring(2, vCode.length);\n" +
                    "            }\n" +
                    "        }\n" +
                    "        charArrForChecked = vCode.split(\"\");\n" +
                    "\n" +
                    "        //GS1---EAN-13-12-14\n" +
                    "        for (var i = charArrForChecked.length - 1; i > 0; i--) {\n" +
                    "\n" +
                    "            if (parseInt(Ean13Code.indexOf(charArrForChecked[i])) > -1) {\n" +
                    "                break;\n" +
                    "            }\n" +
                    "\n" +
                    "\n" +
                    "            if (i % 2 == 0) {\n" +
                    "                if (i != 0) {\n" +
                    "                    sumJS += parseInt(charArrForChecked[i - 1])\n" +
                    "                } else {\n" +
                    "                    sumJS += parseInt(charArrForChecked[i])\n" +
                    "                }\n" +
                    "            }\n" +
                    "            else {\n" +
                    "                if (i != 0) {\n" +
                    "                    sumOS += parseInt(charArrForChecked[i - 1])\n" +
                    "                } else {\n" +
                    "                    sumOS += parseInt(charArrForChecked[i])\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "        if (charArrForChecked.length % 2 == 0) {\n" +
                    "\n" +
                    "            totalSum = sumJS + sumOS * 3;\n" +
                    "\n" +
                    "        } else {\n" +
                    "            totalSum = sumJS * 3 + sumOS;\n" +
                    "        }\n" +
                    "\n" +
                    "        var num = parseInt((totalSum / 10)) + (totalSum % 10 > 0 ? 1 : 0);\n" +
                    "        //取得条码最后一位\n" +
                    "        var dd = parseInt(charArrForChecked[vCode.length - 1]);\n" +
                    "\n" +
                    "        if (num * 10 - totalSum == dd) {\n" +
                    "            codeType = \"EAN\";\n" +
                    "            return codeType;\n" +
                    "        }\n" +
                    "\n" +
                    "    }\n" +
                    "    return codeType = \"\";\n" +
                    "}", "JavaScript", 1, null);

            // Get the functionName defined in JavaScriptCode
            Object obj = scope.get("GetInformationFromCode", scope);
            if (obj instanceof Function) {
                Function jsFunction = (Function) obj;
                // Call the function with params
                Object jsResult = jsFunction.call(rhino, scope, scope, params);
                // Parse the jsResult object to a String
                String result = org.mozilla.javascript.Context.toString(jsResult);
                return  result;
            }
        } finally {
            org.mozilla.javascript.Context.exit();
        }
        return  "";
    }

    public static String GetInfoFromRange(Object[] params){
        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        // Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();
            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            rhino.evaluateString(scope, "function GetInfoFromRange(Code, Range) {\n" +
                    "\n" +
                    "    if (Range != \"\") {\n" +
                    "        var Ranges = Range.split(\",\");\n" +
                    "        return Code.substring(parseInt(Ranges[0]) - 1, parseInt(Ranges[1]) + parseInt(Ranges[0]));\n" +
                    "    }\n" +
                    "}", "JavaScript", 1, null);

            // Get the functionName defined in JavaScriptCode
            Object obj = scope.get("GetInfoFromRange", scope);
            if (obj instanceof Function) {
                Function jsFunction = (Function) obj;
                // Call the function with params
                Object jsResult = jsFunction.call(rhino, scope, scope, params);
                // Parse the jsResult object to a String
                String result = org.mozilla.javascript.Context.toString(jsResult);
                return result;
            }
        } finally {
            org.mozilla.javascript.Context.exit();
        }
        return "";
    }

    public static String GetDateInfoFromRange(Object[] params){
        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        // Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();
            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            rhino.evaluateString(scope, "function GetDateInfoFromRange(Code, Range) {\n" +
                    "\n" +
                    "    if (Range != \"\") {\n" +
                    "        var Ranges = Range.split(',');\n" +
                    "        var dateType = Range.split('|')[1];\n" +
                    "        return GetDateInformation(Code.substring(parseInt(Ranges[0]) - 1, parseInt(Ranges[1].split('|')[0]) + parseInt(Ranges[0])), dateType);\n" +
                    "    } else {\n" +
                    "        return \"\";\n" +
                    "    }\n" +
                    "}\n" +
                    "//根据日期格式格式化日期为要显示日期\n" +
                    "//dateString--要格式的日期字条串 如：YYMMDD=>>150928  YYJJJ=>>15182   MMYY==> >1115\n" +
                    "//dateType\"--格式：如YYMMDD\n" +
                    "function GetDateInformation(dateString, dateType) {\n" +
                    "    var myDate = new Date();\n" +
                    "    //去当前时间的前两位 如：2015-----20\n" +
                    "    var ye = myDate.getFullYear().toString().substring(0, 2);\n" +
                    "    var result = 0;\n" +
                    "\n" +
                    "    switch (dateType) {\n" +
                    "        case \"YYMMDD\":\n" +
                    "            result = ye + dateString.substring(0, 2) + \"-\" + dateString.substring(2, 4) + \"-\" + dateString.substring(4, 6);\n" +
                    "            break;\n" +
                    "        case \"YYJJJ\":\n" +
                    "            var flag = 0;\n" +
                    "            var year = parseInt(ye + dateString.substring(0, 2));\n" +
                    "            var Month = 0;\n" +
                    "            var Days = 0;\n" +
                    "            var monthIndex = 0;\n" +
                    "            var day = parseInt(dateString.substring(2, 5));\n" +
                    "            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 100)) flag = 1;\n" +
                    "            else flag = 0;\n" +
                    "            var days = [0, 31, 59 + flag, 90 + flag, 120 + flag, 151 + flag, 181 + flag, 212 + flag, 243 + flag, 273 + flag, 304 + flag, 334 + flag, 365 + flag];\n" +
                    "            for (var i = 0; i < days.length; i++) {\n" +
                    "                if (day <= days[i]) {\n" +
                    "                    Month = i < 10 ? (\"0\" + i) : i;\n" +
                    "                    monthIndex = i - 1;\n" +
                    "                    break;\n" +
                    "                }\n" +
                    "            }\n" +
                    "            Days = (day - days[monthIndex] < 10) ? \"0\" + (day - days[monthIndex]) : (day - days[monthIndex]);\n" +
                    "            result = year + \"-\" + Month + \"-\" + Days;\n" +
                    "            break;\n" +
                    "        case \"MMYY\":\n" +
                    "            year = parseInt(ye + dateString.substring(2, 4));\n" +
                    "\n" +
                    "\n" +
                    "            result = ye + dateString.substring(2, 4) + \"-\" + dateString.substring(0, 2);\n" +
                    "            break;\n" +
                    "        case \"YYMM\":\n" +
                    "            result = ye + dateString.substring(0, 2) + \"-\" + dateString.substring(2, 4);\n" +
                    "            break;\n" +
                    "        case \"MMYYYY\":\n" +
                    "            result =dateString.substring(2, 6) + \"-\" + dateString.substring(0, 2);\n" +
                    "            break;\n" +
                    "        default:\n" +
                    "            break;\n" +
                    "    }\n" +
                    "    return result;\n" +
                    "\n" +
                    "}", "JavaScript", 1, null);

            // Get the functionName defined in JavaScriptCode
            Object obj = scope.get("GetDateInfoFromRange", scope);
            if (obj instanceof Function) {
                Function jsFunction = (Function) obj;
                // Call the function with params
                Object jsResult = jsFunction.call(rhino, scope, scope, params);
                // Parse the jsResult object to a String
                String result = org.mozilla.javascript.Context.toString(jsResult);
                return result;
            }
        } finally {
            org.mozilla.javascript.Context.exit();
        }
        return "";
    }*/
}
