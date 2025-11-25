package anthony.yublog.service;

import anthony.yublog.dto.category.request.CategoryCreateDTO;
import anthony.yublog.dto.category.request.CategoryDetailVO;
import anthony.yublog.dto.category.response.CategoryListVO;
import anthony.yublog.dto.category.request.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {
    //添加分类标签
    void add(CategoryCreateDTO category);

    //列表查询
    List<CategoryListVO> list();

    //根据id查询
    CategoryDetailVO findById(Integer id);

    boolean delete(Integer id);

    boolean update(CategoryUpdateDTO category);
}
