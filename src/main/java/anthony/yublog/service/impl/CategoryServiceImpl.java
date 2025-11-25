package anthony.yublog.service.impl;

import anthony.yublog.dto.category.request.CategoryCreateDTO;
import anthony.yublog.dto.category.request.CategoryDetailVO;
import anthony.yublog.dto.category.response.CategoryListVO;
import anthony.yublog.dto.category.request.CategoryUpdateDTO;
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

    /**
     * 添加分类名称和别名
     *
     * @param category 分类
     */
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
    public List<CategoryListVO> list() {
        //获取当前用户id
        return categoryMapper.list(getCurrentUserId());
    }

    @Override
    public CategoryDetailVO findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public boolean delete(Integer id) {
        return categoryMapper.delete(id) > 0;
    }

    @Override
    public void update(CategoryUpdateDTO category) {
        if (!categoryIdExist(category.getId())) {
            throw new BizException(ErrorCode.CATEGORY_NOT_FOUND);
        } else if (categoryNameExist(category.getCategoryName(), getCurrentUserId())) {
            throw new BizException(ErrorCode.CATEGORY_NAME_EXISTS);
        } else if (categoryAliasExist(category.getCategoryAlias(), getCurrentUserId())) {
            throw new BizException(ErrorCode.CATEGORY_ALIAS_EXISTS);
        }
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 通过分类 Id
     *
     * @param id 分类id
     * @return true:存在 false:不存在
     */
    public boolean categoryIdExist(Integer id) {
        return categoryMapper.catExistById(id);
    }

    public boolean categoryNameExist(String categoryName, Integer userId) {
        List<CategoryListVO> list = categoryMapper.listByCatNameAndId(categoryName, userId);
        return list != null && !list.isEmpty();
    }

    private boolean categoryAliasExist(String categoryAlias, Integer currentUserId) {
        List<CategoryListVO> list = categoryMapper.listByCatAliasAndId(categoryAlias, currentUserId);
        return list != null && !list.isEmpty();
    }
}
