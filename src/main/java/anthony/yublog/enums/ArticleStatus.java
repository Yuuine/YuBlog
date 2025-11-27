package anthony.yublog.enums;

public enum ArticleStatus {
    DRAFT,          // 草稿
    REVIEWING,      // 审核中
    PUBLISHED,      // 已发布
    ARCHIVED,       // 已归档
    DELETED       // 已删除（逻辑删除）
}