package anthony.yublog.enums;


import lombok.Getter;

@Getter
public enum ArticleStatus {

    DRAFT(0, "草稿", "Draft"),          // 未发布，编辑中
    REVIEWING(1, "审核中", "Reviewing"), // 待审核
    PUBLISHED(2, "已发布", "Published"), // 正式上线
    ARCHIVED(3, "已归档", "Archived"),   // 下架但保留
    DELETED(4, "已删除", "Deleted");

    private final int code;      // 数据库存储的整数代码
    private final String descZh; // 中文描述（i18n 支持）
    private final String descEn; // 英文描述（多语言扩展）// 逻辑删除

    ArticleStatus(int code, String descZh, String descEn) {
        this.code = code;
        this.descZh = descZh;
        this.descEn = descEn;
    }

    // 静态方法：根据代码反查枚举（用于数据库查询结果转换）
    public static ArticleStatus fromCode(int code) {
        for (ArticleStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ArticleStatus code: " + code);
    }
}
