package tyxo.baseappframe.utils.md5;

public class SecurityUtils {
    //自己设置加密密码
    private static byte[] privateKey = "加密密码tyxo".getBytes();

    /**
     * 加密
     */
    public static String encryt(String plainText) {
        Encryption encryption = new Encryption(Encryption.getDefaultCipher(), privateKey);
        String encrypted = encryption.encrypt(plainText);
        return encrypted;
    }
}
