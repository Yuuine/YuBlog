package anthony.yublog.controller;

import anthony.yublog.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result<String> list() {
        log.info("获取文章列表");
        return Result.success("文章数据");
    }
}
