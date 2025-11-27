package anthony.yublog.dto.article.response;

import lombok.Data;

import java.util.List;

@Data
public class ArticleListVO {

    private Integer total;
    private List<ArticleItemVO> items;
}
