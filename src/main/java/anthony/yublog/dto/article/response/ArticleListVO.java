package anthony.yublog.dto.article.response;

import lombok.Data;

import java.util.List;

@Data
public class ArticleListVO<T> {

    private Long total;
    private List<T> items;
}
