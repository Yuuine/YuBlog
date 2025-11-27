package anthony.yublog.service.impl;

import anthony.yublog.dto.article.request.ArticleAddDTO;
import anthony.yublog.dto.article.request.ArticleListDTO;
import anthony.yublog.dto.article.response.ArticleListVO;
import anthony.yublog.entity.Article;
import anthony.yublog.mapper.ArticleMapper;
import anthony.yublog.service.ArticleService;
import anthony.yublog.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    private Integer getCurrentUserId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        return (Integer) map.get("id");
    }

    /**
     * 添加文章
     *
     * @param articleAddDTO 文章信息
     */
    @Override
    public void addArticle(ArticleAddDTO articleAddDTO) {

        Article article = new Article();
        BeanUtils.copyProperties(articleAddDTO, article);

        Integer createUserId = getCurrentUserId();
        article.setCreateUser(createUserId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        articleMapper.addArticle(article);
    }

    @Override
    public ArticleListVO listArticles(ArticleListDTO articleListDTO) {
        return null;
    }
}
