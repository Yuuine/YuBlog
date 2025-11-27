package anthony.yublog.service.impl;

import anthony.yublog.dto.article.request.ArticleAddDTO;
import anthony.yublog.dto.article.request.ArticleListDTO;
import anthony.yublog.dto.article.request.ArticleUpdateDTO;
import anthony.yublog.dto.article.response.ArticleDetailVO;
import anthony.yublog.dto.article.response.ArticleItemVO;
import anthony.yublog.dto.article.response.ArticleListVO;
import anthony.yublog.entity.Article;
import anthony.yublog.enums.ArticleStatus;
import anthony.yublog.exception.BizException;
import anthony.yublog.exception.ErrorCode;
import anthony.yublog.mapper.ArticleMapper;
import anthony.yublog.service.ArticleService;
import anthony.yublog.utils.ThreadLocalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID
     */
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

    /**
     * 获取文章列表
     *
     * @param articleListDTO 查询条件
     * @return 文章列表
     */
    @Override
    public ArticleListVO<ArticleItemVO> listArticles(ArticleListDTO articleListDTO) {

        // 获取当前用户ID，用于查询该用户的文章
        Integer createUser = getCurrentUserId();
        // 获取分类ID，用于按分类筛选文章
        Integer categoryId = articleListDTO.getCategoryId();
        // 获取文章状态，用于按状态筛选文章
        ArticleStatus state = articleListDTO.getState();

        // 设置分页参数
        PageHelper.startPage(articleListDTO.getPageNum(), articleListDTO.getPageSize());

        // 调用mapper查询文章列表
        List<ArticleItemVO> articleList = articleMapper.listArticles(createUser, categoryId, state.name());

        // 封装分页信息
        PageInfo<ArticleItemVO> pageInfo = new PageInfo<>(articleList);

        // 构造返回结果对象
        ArticleListVO<ArticleItemVO> articleListVO = new ArticleListVO<>();
        articleListVO.setTotal(pageInfo.getTotal());
        articleListVO.setItems(pageInfo.getList());

        return articleListVO;
    }

    /**
     * 根据ID查询文章详情
     *
     * @param id 文章ID
     * @return 文章详情
     */
    @Override
    public ArticleDetailVO findById(Integer id) {
        return articleMapper.findById(id);
    }

    /**
     * 修改文章
     *
     * @param articleUpdateDTO 修改信息
     */
    @Override
    public void updateArticle(ArticleUpdateDTO articleUpdateDTO) {
        articleUpdateDTO.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticle(articleUpdateDTO);

    }

    /**
     * 删除文章
     *
     * @param id 文章ID
     */
    @Override
    public void delete(Integer id) {
        int deletedRows = articleMapper.delete(id);
        if (deletedRows == 0) {
            throw new BizException(ErrorCode.POST_NOT_FOUND);
        }
    }

    /**
     * 判断文章ID是否存在
     *
     * @param id 文章ID
     * @return true：存在，false：不存在
     */
    private boolean articleIdExist(Integer id) {
        return articleMapper.articleIdExist(id);
    }
}
