package anthony.yublog.dto.article.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleViewCountVO {
    private Integer id;
    private Long viewCount;
}
