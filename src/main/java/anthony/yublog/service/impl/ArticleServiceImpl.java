package anthony.yublog.service.impl;

import anthony.yublog.dto.article.request.ArticleAddDTO;
import anthony.yublog.dto.article.request.ArticleListDTO;
import anthony.yublog.dto.article.request.ArticleUpdateDTO;
import anthony.yublog.dto.article.response.ArticleDetailVO;
import anthony.yublog.dto.article.response.ArticleItemVO;
import anthony.yublog.dto.article.response.ArticleListVO;
import anthony.yublog.dto.article.response.ArticleViewCountVO;
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
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@EnableScheduling
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    // 文章浏览数缓存key
    private static final String VIEW_KEY_PREFIX = "article:viewCount:";

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
        List<ArticleItemVO> articleList = articleMapper.listArticles(createUser, categoryId, state != null ? state.name() : null);

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
     * 文章浏览数
     *
     * @param id 文章ID
     */
    @Override
    public ArticleViewCountVO viewCount(Integer id) {
        //使用 redis 缓存文章浏览量
        String key = VIEW_KEY_PREFIX + id;

        //Redis 原子性增1 返回增后值
        Long current = stringRedisTemplate.opsForValue().increment(key, 1L);
        // 给 key 设置过期时间，防止长期无人访问的文章一直占内存
        stringRedisTemplate.expire(key, Duration.ofDays(30));
        //返回浏览数
        ArticleViewCountVO vo = new ArticleViewCountVO();
        vo.setId(id);
        vo.setViewCount(current);
        return vo;
    }

    /**
     * 每 3 分钟把 Redis 累计的浏览量刷回 MySQL
     * SCAN + pipeline + 批量更新 + 删除已同步 key
     */
    @Scheduled(fixedRate = 180000, initialDelay = 600000) // 3 分钟刷回到数据库, 初始延迟 10 分钟
    @Transactional(rollbackFor = Exception.class)
    public void syncViewCountToDB() {

        try (Cursor<String> cursor = stringRedisTemplate.scan(ScanOptions.scanOptions()
                .match("article:view:*").count(1000).build())) {
            List<ArticleViewCountVO> batch = new ArrayList<>(1000);

            while (cursor.hasNext()) {
                String key = cursor.next();
                String val = stringRedisTemplate.opsForValue().get(key);
                if (val == null) {
                    continue;
                }
                Integer articleId = Integer.parseInt(key.substring(VIEW_KEY_PREFIX.length()));
                Long viewCount = Long.parseLong(val);
                batch.add(new ArticleViewCountVO(articleId, viewCount));
                // 批量更新
                if (batch.size() >= 1000) {
                    flushBatch(batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                flushBatch(batch);
            }
        } catch (Exception e) {
            log.error("同步文章浏览数到数据库失败", e);
        }
    }

    public void flushBatch(List<ArticleViewCountVO> batch) {
        if (batch == null || batch.isEmpty()) {
            return;
        }
        articleMapper.batchUpdateViewCount(batch);
        List<String> keysToDelete = batch.stream()
                .map(vo -> VIEW_KEY_PREFIX + vo.getId())
                .toList();
        stringRedisTemplate.delete(keysToDelete);
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
