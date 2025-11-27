package anthony.yublog.dto.article.request;

import anthony.yublog.enums.ArticleStatus;
import lombok.Data;

@Data
public class ArticleListDTO {

    private Integer pageNum;
    private Integer pageSize;
    private Integer categoryId;
    private ArticleStatus state;
}
