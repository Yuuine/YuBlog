package anthony.yublog.dto.article.response;

import anthony.yublog.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}