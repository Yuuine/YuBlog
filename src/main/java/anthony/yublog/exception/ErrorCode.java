package anthony.yublog.exception;

import lombok.Getter;

/**
 * 业务错误码枚举
 * - 1000~1999: 用户相关
 * - 2000~2999: 文章相关
 * - 3000~3999: 评论相关
 * - 5000~5999: 系统通用
 */
@Getter
public enum ErrorCode {


    // 通用
    VALIDATION_FAILED(400, "请求参数不合法"),
    ACCESS_DENIED(403, "无权限访问"),
    RESOURCE_NOT_FOUND(404, "资源不存在"),

    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户名已存在"),
    PASSWORD_INCORRECT(1003, "密码错误"),
    INVALID_TOKEN(1004, "无效的登录凭证"),

    POST_NOT_FOUND(2001, "文章不存在"),
    POST_FORBIDDEN(2002, "无权操作该文章"),

    COMMENT_NOT_FOUND(3001, "评论不存在");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
