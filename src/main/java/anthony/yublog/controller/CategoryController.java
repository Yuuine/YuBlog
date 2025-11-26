package anthony.yublog.controller;

import anthony.yublog.dto.category.request.CategoryCreateDTO;
import anthony.yublog.dto.category.request.CategoryDetailVO;
import anthony.yublog.dto.category.response.CategoryListVO;
import anthony.yublog.dto.category.request.CategoryUpdateDTO;
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
        categoryService.add(category);
        return Result.success();
    }

    /**
     * 功能2：
     * 查询文章分类标签
     * 查询用户个人创建的所有文章分类标签
     */
    @GetMapping
    public Result<List<CategoryListVO>> list() {
        List<CategoryListVO> cs = categoryService.list();
        return Result.success(cs);
    }

    /**
     * 功能3：
     * 查询文章分类标签详情
     * 根据文章分类标签id查询文章分类标签详情，两个字段：1. 分类标签名称 2. 分类标签别名
     */
    @GetMapping("/detail")
    public Result<CategoryDetailVO> detail(Integer id) {
        CategoryDetailVO c = categoryService.findById(id);
        return Result.success(c);
    }

    /**
     * 功能4：
     * 修改分类标签
     */
    @PutMapping
    public Result<Object> update(@RequestBody @Validated CategoryUpdateDTO category) {
        categoryService.update(category);
        return Result.success();
    }
    /**
     * 功能5：
     * 删除分类标签
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}
