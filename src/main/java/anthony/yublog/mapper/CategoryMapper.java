package anthony.yublog.mapper;

import anthony.yublog.dto.CategoryCreateDTO;
import anthony.yublog.dto.CategoryDetailDTO;
import anthony.yublog.dto.CategoryListDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //添加分类
    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " +
            "values(#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(CategoryCreateDTO category);

    @Select("select * from category where create_user = #{userId}")
    List<CategoryListDTO> list(Integer userId);

    @Select("select * from category where id = #{id}")
    CategoryDetailDTO findById(Integer id);
}
