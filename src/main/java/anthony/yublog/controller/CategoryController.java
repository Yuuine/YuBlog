package anthony.yublog.controller;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryDetailDTO;
import anthony.yublog.dto.CategoryListDTO;
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
     */
    //TODO: 全局异常处理功能待优化
    @PostMapping
    public Result<Object> add(@RequestBody @Validated CategoryCreateDTO category) {
        int result = categoryService.add(category);
        if (result == 1) {
            return Result.error("分类名称已存在");
        } else if (result == 2) {
            return Result.error("分类别名已存在");
        }
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
    public Result<CategoryDetailDTO> detail(Integer id) {
        CategoryDetailDTO c = categoryService.findById(id);
        return Result.success(c);
    }
}
