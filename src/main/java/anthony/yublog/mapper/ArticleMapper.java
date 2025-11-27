package anthony.yublog.mapper;

import anthony.yublog.dto.article.request.ArticleUpdateDTO;
import anthony.yublog.dto.article.response.ArticleDetailVO;
import anthony.yublog.dto.article.response.ArticleItemVO;
import anthony.yublog.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void addArticle(Article article);

    List<ArticleItemVO> listArticles(Integer createUser, Integer categoryId, String state);

    @Select("select * from article where id = #{id}")
    ArticleDetailVO findById(Integer id);

    @Insert("update article set title = #{title}, content = #{content}, cover_img = #{coverImg}, state = #{state}, " +
            "update_time = #{updateTime} where id = #{id}")
    void updateArticle(ArticleUpdateDTO articleUpdateDTO);

    @Delete("delete from article where id = #{id}")
    int delete(Integer id);

    @Select("SELECT EXISTS(SELECT 1 FROM article WHERE id = #{id})")
    boolean articleIdExist(Integer id);
}