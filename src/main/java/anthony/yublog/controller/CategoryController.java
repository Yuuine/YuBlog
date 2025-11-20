package anthony.yublog.controller;

import anthony.yublog.pojo.Category;
import anthony.yublog.pojo.Result;
import anthony.yublog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     */
    @PostMapping
    public Result<Object> add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /*
        查询文章分类
     */
    @GetMapping
    public Result<Object> list() {
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }
}
