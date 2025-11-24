package anthony.yublog.exception;

/**
 * 业务异常类
 * 用于表示用户可理解的、非系统性的错误（如参数错误、资源不存在等）
 * 不应被记录为 ERROR 日志，通常返回 400 状态码
 */
public class BizException  extends RuntimeException{

    private final int code;

    /**
     * 构造方法：通过错误码和消息创建异常
     *
     * @param code    业务错误码（如 1001）
     * @param message 错误提示信息（用户可见）
     */
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造方法：通过枚举错误码创建异常
     *
     * @param errorCode 预定义的错误码枚举
     */
    public BizException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    // Getter
    public int getCode() {
        return code;
    }
}
