package anthony.yublog.service;

import anthony.yublog.dto.article.request.ArticleAddDTO;
import anthony.yublog.dto.article.request.ArticleListDTO;
import anthony.yublog.dto.article.request.ArticleUpdateDTO;
import anthony.yublog.dto.article.response.ArticleDetailVO;
import anthony.yublog.dto.article.response.ArticleItemVO;
import anthony.yublog.dto.article.response.ArticleListVO;
import anthony.yublog.dto.article.response.ArticleViewCountVO;

public interface ArticleService {

    void addArticle(ArticleAddDTO articleAddDTO);

    ArticleListVO<ArticleItemVO> listArticles(ArticleListDTO articleListDTO);

    ArticleDetailVO findById(Integer id);

    void updateArticle(ArticleUpdateDTO articleUpdateDTO);

    void delete(Integer id);

    ArticleViewCountVO viewCount(Integer id);
}
