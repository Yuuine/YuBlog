package anthony.yublog.service.impl;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryDetailDTO;
import anthony.yublog.dto.CategoryListDTO;
import anthony.yublog.dto.CategoryUpdateDTO;
import anthony.yublog.exception.BizException;
import anthony.yublog.exception.ErrorCode;
import anthony.yublog.mapper.CategoryMapper;
import anthony.yublog.service.CategoryService;
import anthony.yublog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static anthony.yublog.utils.AliasUtil.generateAlias;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Integer getCurrentUserId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        return (Integer) map.get("id");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //TODO: 并发问题待处理
    public void add(CategoryCreateDTO category) {
        //统一获取userId
        Integer userId = getCurrentUserId();
        //判断传入的分类名称是否已存在, 存在则抛出异常
        String categoryName = category.getCategoryName();
        if (categoryNameExist(categoryName, userId)) {
            throw new BizException(ErrorCode.CATEGORY_NAME_EXISTS);
        }
        //判断传入的分类别名是否已存在, 存在则抛出异常
        if (categoryAliasExist(category.getCategoryAlias(), userId)) {
            throw new BizException(ErrorCode.CATEGORY_ALIAS_EXISTS);
        }
        //判断用户是否提供别名，没有自动生成别名
        String alias = category.getCategoryAlias();
        //返回生成好的别名
        String newAlias = generateAlias(category.getCategoryName(), alias);
        log.info("已生成别名：{}", newAlias);

        category.setCreateUser(userId);
        category.setCategoryAlias(newAlias);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    @Override
    public List<CategoryListDTO> list() {
        //获取当前用户id
        return categoryMapper.list(getCurrentUserId());
    }

    @Override
    public CategoryDetailDTO findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public boolean delete(Integer id) {
        return categoryMapper.delete(id) > 0;
    }

    @Override
    public boolean update(CategoryUpdateDTO category) {
        return categoryMapper.update(category.getId(), category.getCategoryName(),
                category.getCategoryAlias()) > 0;
    }

    public boolean categoryNameExist(String categoryName, Integer userId) {
        List<CategoryListDTO> list = categoryMapper.listByCatNameAndId(categoryName, userId);
        return list != null && !list.isEmpty();
    }

    private boolean categoryAliasExist(String categoryAlias, Integer currentUserId) {
        List<CategoryListDTO> list = categoryMapper.listByCatAliasAndId(categoryAlias, currentUserId);
        return list != null && !list.isEmpty();
    }
}
