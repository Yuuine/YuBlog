package anthony.yublog.service;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryListDTO;
import anthony.yublog.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(CategoryCreateDTO category);

    //列表查询
    List<CategoryListDTO> list();

    //根据id查询
    Category findById(Integer id);
}
