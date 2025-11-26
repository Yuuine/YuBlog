package anthony.yublog.exception;

import lombok.Getter;

/**
 * 业务错误码枚举
 * 该枚举定义了系统中的所有业务错误码，按照功能模块进行分组，便于管理和扩展。
 * 错误码设计原则：
 * - 1000~1999: 用户相关错误（用户认证、注册、个人信息等）
 * - 2000~2999: 文章相关错误（文章创建、编辑、发布、查询等）
 * - 3000~3999: 评论相关错误（评论提交、审核、删除等）
 * - 4000~4999: 分类相关错误（分类标签创建、查询、别名管理等）
 * - 5000~5999: 系统通用错误（参数校验、权限、资源访问、内部异常等）
 * <p>
 * 每个错误码包含一个唯一的整数代码和描述性消息，便于在异常处理中统一使用。
 */
@Getter
public enum ErrorCode {

    // 系统通用错误 (5000~5999)
    VALIDATION_FAILED(5001, "请求参数不合法"),
    ACCESS_DENIED(5002, "无权限访问"),
    RESOURCE_NOT_FOUND(5003, "资源不存在"),
    INTERNAL_SERVER_ERROR(5004, "系统内部错误"),
    DATABASE_ERROR(5005, "数据库操作失败"),
    TOKEN_EXPIRED(5006, "令牌已过期"),
    INVALID_REQUEST(5007, "无效的请求"),
    OPERATION_FAILED(5008, "操作失败，请重试"),
    FILE_UPLOAD_FAILED(5009, "文件上传失败"),
    CONFIGURATION_ERROR(5010, "系统配置错误"),

    // 用户相关错误 (1000~1999)
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户名已存在"),
    EMAIL_ALREADY_EXISTS(1003, "邮箱已存在"),
    USER_OR_PASSWORD_ERROR(1004, "用户名或密码错误"),
    PASSWORD_INCORRECT(1005, "密码错误"),
    INVALID_TOKEN(1006, "无效的登录凭证"),
    USER_DISABLED(1007, "用户已被禁用"),
    VERIFICATION_CODE_INVALID(1008, "验证码无效"),
    USER_REGISTRATION_FAILED(1009, "用户注册失败"),
    PASSWORD_RESET_FAILED(10010, "密码重置失败"),
    PROFILE_UPDATE_FAILED(1011, "个人信息更新失败"),
    USER_PASSWORD_MISMATCH(1010, "原密码与新密码一致"),
    PASSWORDS_NOT_MATCH(1101, "两次密码不一致"),
    NEW_PASSWORD_SAME_AS_OLD(1102, "新密码不能和旧密码一致"),

    // 文章相关错误 (2000~2999)
    POST_NOT_FOUND(2001, "文章不存在"),
    POST_FORBIDDEN(2002, "无权操作该文章"),
    POST_TITLE_EMPTY(2003, "文章标题不能为空"),
    POST_CONTENT_EMPTY(2004, "文章内容不能为空"),
    POST_PUBLISH_FAILED(2005, "文章发布失败"),
    POST_DELETE_FAILED(2006, "文章删除失败"),
    POST_UPDATE_FAILED(2007, "文章更新失败"),
    POST_CATEGORY_INVALID(2008, "文章分类无效"),
    POST_TAG_LIMIT_EXCEEDED(2009, "文章标签数量超出限制"),
    POST_DRAFT_SAVE_FAILED(2010, "草稿保存失败"),

    // 评论相关错误 (3000~3999)
    COMMENT_NOT_FOUND(3001, "评论不存在"),
    COMMENT_FORBIDDEN(3002, "无权操作该评论"),
    COMMENT_CONTENT_EMPTY(3003, "评论内容不能为空"),
    COMMENT_SUBMIT_FAILED(3004, "评论提交失败"),
    COMMENT_DELETE_FAILED(3005, "评论删除失败"),
    COMMENT_REPLY_INVALID(3006, "无效的回复目标"),
    COMMENT_SPAM_DETECTED(3007, "评论涉嫌垃圾信息"),
    COMMENT_AUDIT_FAILED(3008, "评论审核失败"),
    COMMENT_RATE_LIMIT(3009, "评论频率过高，请稍后重试"),
    COMMENT_PARENT_NOT_FOUND(3010, "父评论不存在"),

    // 分类相关错误 (4000~4999)
    CATEGORY_NOT_FOUND(4001, "分类不存在"),
    CATEGORY_NAME_EXISTS(4002, "分类名称已存在"),
    CATEGORY_ALIAS_EXISTS(4003, "分类别名已存在"),
    CATEGORY_CREATE_FAILED(4004, "分类创建失败"),
    CATEGORY_UPDATE_FAILED(4005, "分类更新失败"),
    CATEGORY_DELETE_FAILED(4006, "分类删除失败"),
    CATEGORY_FORBIDDEN(4007, "无权操作该分类"),
    CATEGORY_NAME_EMPTY(4008, "分类名称不能为空"),
    CATEGORY_ALIAS_INVALID(4009, "分类别名无效"),
    CATEGORY_LIMIT_EXCEEDED(4010, "分类数量超出限制");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}