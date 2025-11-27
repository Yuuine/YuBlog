package anthony.yublog.dto.article.request;

import anthony.yublog.enums.ArticleStatus;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class ArticleUpdateDTO {

    private Integer id;
    private String title;
    private String content;
    @URL
    private String coverImg;
    private ArticleStatus state;
    private Integer categoryId;
    private LocalDateTime updateTime;
}
