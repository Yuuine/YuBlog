package anthony.yublog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * 对明文密码进行hash加密
     */
    public  static String encode(String password) {
        return encoder.encode(password);
    }
    /**
     * 验证明文密码和hash值是否匹配
     */
    public static boolean matches(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
