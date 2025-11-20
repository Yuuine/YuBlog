package anthony.yublog.service.impl;

import anthony.yublog.mapper.CategoryMapper;
import anthony.yublog.pojo.Category;
import anthony.yublog.service.CategoryService;
import anthony.yublog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        category.setCreateTime(java.time.LocalDateTime.now());
        category.setUpdateTime(java.time.LocalDateTime.now());

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        //获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }
}
