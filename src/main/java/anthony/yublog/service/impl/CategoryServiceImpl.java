package anthony.yublog.service.impl;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryDetailDTO;
import anthony.yublog.dto.CategoryListDTO;
import anthony.yublog.mapper.CategoryMapper;
import anthony.yublog.service.CategoryService;
import anthony.yublog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static anthony.yublog.utils.AliasUtil.generateAlias;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(CategoryCreateDTO category) {
        //判断用户是否提供别名，没有自动生成别名
        String alias = category.getCategoryAlias();
        //返回生成好的别名
        String newAlias = generateAlias(category.getCategoryName(), alias);
        log.info("已生成别名：{}", newAlias);

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        category.setCreateUser(userId);
        category.setCategoryAlias(newAlias);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    @Override
    public List<CategoryListDTO> list() {
        //获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    @Override
    public CategoryDetailDTO findById(Integer id) {
        return categoryMapper.findById(id);
    }
}
