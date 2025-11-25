package anthony.yublog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// TODO: 加密工具类优化
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
     * 在登录验证密码时使用
     * 匹配密码成功返回true，否则返回false
     */
    public static boolean matches(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
