package anthony.yublog.mapper;

import anthony.yublog.dto.category.request.CategoryCreateDTO;
import anthony.yublog.dto.category.request.CategoryDetailVO;
import anthony.yublog.dto.category.request.CategoryUpdateDTO;
import anthony.yublog.dto.category.response.CategoryListVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //添加分类
    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " +
            "values(#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(CategoryCreateDTO category);

    @Select("select * from category where create_user = #{userId}")
    List<CategoryListVO> list(Integer userId);

    @Select("select * from category where id = #{id}")
    CategoryDetailVO findById(Integer id);

    @Select("select 1 from category where category_name = #{categoryName} and create_user = #{userId} limit 1")
    Integer catExistByNameAndId(String categoryName, Integer userId);

    @Select("select 1 from category where category_alias = #{categoryAlias} and create_user = #{userId} limit 1")
    Integer catExistByAliasAndId(String categoryAlias, Integer userId);

    @Delete("delete from category where id = #{id}")
    Integer delete(Integer id);

    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time = #{updateTime} where id = #{id}")
    void update(CategoryUpdateDTO categoryUpdateDTO);

    @Select("select COUNT(*) > 0 from category where id = #{id}")
    boolean catExistById(Integer id);
}
