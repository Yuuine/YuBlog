package anthony.yublog.entity;


import anthony.yublog.enums.ArticleStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// @Data 注解是Lombok提供的一个注解，用于自动生成getter、setter、toString、equals、hashCode方法
public class Article {
    private Integer id;//主键ID
    private String title;//文章标题
    private String content;//文章内容
    private String coverImg;//封面图像
    private ArticleStatus state;//发布状态
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}