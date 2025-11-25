package anthony.yublog.exception;

import lombok.Getter;

/**
 * 业务异常类
 * 用于表示用户可理解的、非系统性的错误（如参数错误、资源不存在等）
 * 不应被记录为 ERROR 日志，返回 400 状态码
 */
@Getter
public class BizException extends RuntimeException {

    // Getter
    private final String code;

    /**
     * 构造方法：通过错误码和消息创建异常
     *
     * @param code    业务错误码（如 1001）
     * @param message 错误提示信息（用户可见）
     */
    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 构造方法：通过枚举错误码创建异常
     *
     * @param errorCode 预定义的错误码枚举
     */
    public BizException(ErrorCode errorCode) {
        this(String.valueOf(errorCode.getCode()), errorCode.getMessage());
    }

    public BizException(ErrorCode errorCode, Throwable cause) {
        this(String.valueOf(errorCode.getCode()), errorCode.getMessage(), cause);
    }

    /**
     * 快捷创建方法
     */
    public static BizException of(String code, String message) {
        return new BizException(code, message);
    }

    public static BizException of(ErrorCode errorCode) {
        return new BizException(errorCode);
    }

    public static BizException of(String message) {
        return new BizException("BIZ_ERROR", message);
    }

}
