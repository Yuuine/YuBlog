package anthony.yublog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


/**
 * JWT工具类
 *
 * @author Yuuine
 */
@Component
public final class JwtUtil {

    private static String secretKey;
    private static long expirationTime;

    // 从application.yaml中获取密钥
    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtUtil.secretKey = secretKey;
    }

    // 从application.yaml中获取过期时间
    @Value("${jwt.expiration}") // 120小时默认值
    public void setExpirationTime(long expirationTime) {
        JwtUtil.expirationTime = expirationTime;
    }

    /**
     * 生成JWT Token
     * 接收业务数据,生成token并返回
     *
     * @param claims 声明信息
     * @return token字符串
     */
    public static String genToken(Map<String, Object> claims) {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalStateException("JWT密钥未配置");
        }

        return JWT.create().withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 解析并验证JWT Token
     *
     * @param token JWT token
     * @return 声明信息
     * @throws JWTVerificationException 令牌验证失败
     */
    public static Map<String, Object> parseToken(String token) {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalStateException("JWT密钥未配置");
        }

        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return jwt.getClaim("claims").asMap();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token验证失败: " + e.getMessage(), e);
        }
    }

}
