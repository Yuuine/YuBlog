package anthony.yublog.service;

import anthony.yublog.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    //列表查询
    List<Category> list();
}
