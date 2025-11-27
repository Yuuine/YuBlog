package anthony.yublog.controller;

import anthony.yublog.dto.article.request.ArticleAddDTO;
import anthony.yublog.dto.article.request.ArticleListDTO;
import anthony.yublog.dto.article.request.ArticleUpdateDTO;
import anthony.yublog.dto.article.response.ArticleDetailVO;
import anthony.yublog.dto.article.response.ArticleItemVO;
import anthony.yublog.dto.article.response.ArticleListVO;
import anthony.yublog.entity.Result;
import anthony.yublog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result<Object> add(@RequestBody @Validated ArticleAddDTO articleAddDTO) {
        articleService.addArticle(articleAddDTO);
        log.info("添加文章");
        return Result.success();
    }


    @GetMapping
    public Result<ArticleListVO<ArticleItemVO>> list(@Validated ArticleListDTO articleListDTO) {
        ArticleListVO<ArticleItemVO> articleListVO = articleService.listArticles(articleListDTO);
        log.info("查询文章列表");
        return Result.success(articleListVO);
    }

    @GetMapping("/detail")
    public Result<ArticleDetailVO> detail(@RequestParam Integer id) {
        ArticleDetailVO articleDetailVO = articleService.findById(id);
        log.info("查询文章详情");
        return Result.success(articleDetailVO);
    }

    @PutMapping
    public Result<Object> update(@RequestBody @Validated ArticleUpdateDTO articleUpdateDTO) {
        articleService.updateArticle(articleUpdateDTO);
        log.info("修改文章");
        return Result.success();
    }
}
