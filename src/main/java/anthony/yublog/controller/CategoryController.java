package anthony.yublog.controller;

import anthony.yublog.pojo.Category;
import anthony.yublog.pojo.Result;
import anthony.yublog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<Object> add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }
}
