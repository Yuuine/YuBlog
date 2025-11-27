package anthony.yublog.dto.article.response;

import anthony.yublog.enums.ArticleStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleItemVO {
    private Integer id;
    private String title;
    private String content;
    private String coverImg;
    private ArticleStatus state;
    private Integer categoryId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}