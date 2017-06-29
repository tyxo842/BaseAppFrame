package tyxo.baseappframe.utils.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <core>字符串的工具类</core>
 * @author tyxo
 */
public class MD5Util {

    //md5加密   来自 小罗童鞋
    public static String encode(String pwd){
        //创建加密类型对象，它可以生成一个二进制数组
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] barr = digest.digest(pwd.getBytes());

            //  0000  0000 1111 1111 -0xff
            //  0100  0000 0010 1111
            //	0000  0000 0010 1111
            StringBuffer buffer = new StringBuffer();
            for (byte b : barr) {
                //取二进制数字的后八位
                int i = b & 0xff;//可以在后面+1-2等等操作,专业术语叫加盐
                //将生成的数字进行转换，并转化为十六进制的字符串
                String hexString = Integer.toHexString(i);
//				System.out.println("hexString : " + hexString);
//				Log.d(SettingIntemView_relativelayout.class.getName(), name+":"+value);
//				Log.d("ly", name+":"+value);
                //如果转化后的十六进制的字符串长度为1,那将补零,使其为2位
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                buffer.append(hexString);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final static String getMD5(String str) {
        if (str != null) {
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            try {
                byte[] strBytes = str.getBytes();
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                messageDigest.update(strBytes);
                // 获得密文
                byte[] md = messageDigest.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char arr[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    arr[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    arr[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(arr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
