package anthony.yublog.service;

import anthony.yublog.dto.category.request.CategoryCreateDTO;
import anthony.yublog.dto.category.request.CategoryDetailDTO;
import anthony.yublog.dto.category.request.CategoryListDTO;
import anthony.yublog.dto.category.request.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {
    //添加分类标签
    void add(CategoryCreateDTO category);

    //列表查询
    List<CategoryListDTO> list();

    //根据id查询
    CategoryDetailDTO findById(Integer id);

    boolean delete(Integer id);

    boolean update(CategoryUpdateDTO category);
}
