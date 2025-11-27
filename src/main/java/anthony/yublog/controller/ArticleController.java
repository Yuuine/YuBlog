package anthony.yublog.controller;

import anthony.yublog.dto.article.ArticleAddDTO;
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


    @GetMapping("/list")
    public Result<String> list() {
        log.info("获取文章列表");
        return Result.success("文章数据");
    }
}
