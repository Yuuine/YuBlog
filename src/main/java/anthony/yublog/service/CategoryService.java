package anthony.yublog.service;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryDetailDTO;
import anthony.yublog.dto.CategoryListDTO;

import java.util.List;

public interface CategoryService {
    //添加分类标签
    void add(CategoryCreateDTO category);

    //列表查询
    List<CategoryListDTO> list();

    //根据id查询
    CategoryDetailDTO findById(Integer id);
}
