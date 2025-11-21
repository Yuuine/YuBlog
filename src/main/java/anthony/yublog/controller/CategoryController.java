package anthony.yublog.controller;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryListDTO;
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
     * 功能1：
     * 添加分类标签
     * 添加新的文章标签，必须项：文章分类名称
     * TODO: 文章分类别名允许用户输入
     * TODO: 文章分类别名如果不输入，默系统默认创建。汉字别名创建为汉字的拼音首字母小写，如“测试”-“cs
     * TODO: 英文别名默认为首个单词的前三个字母小写，如“travelNote”-“tra”
     */
    @PostMapping
    public Result<Object> add(@RequestBody @Validated CategoryCreateDTO category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * 功能2：
     * 查询文章分类标签
     * 查询用户个人创建的所有文章分类标签
     */
    @GetMapping
    public Result<Object> list() {
        List<CategoryListDTO> cs = categoryService.list();
        return Result.success(cs);
    }

    /**
     * 功能3：
     * 查询文章分类标签详情
     * 根据文章分类标签id查询文章分类标签详情，两个字段：1. 分类标签名称 2. 分类标签别名
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category c = categoryService.findById(id);
        return Result.success(c);
    }
}
