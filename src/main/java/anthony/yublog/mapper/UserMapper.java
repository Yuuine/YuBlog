package anthony.yublog.mapper;

import anthony.yublog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    // 查询用户名是否存在
    @Select("SELECT COUNT(*) > 0 FROM user WHERE username = #{username}")
    Boolean existByUserName(String username);

    //通过用户名查询用户的详细信息
    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUserName(String username);

    @Insert("insert into user(username, password, create_time, update_time) " +
            " values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    @Update("update user set nickname = #{nickname}, email = #{email}, " +
            "update_time = #{updateTime} where id = #{id} ")
    void update(User user);

    @Update("update user set user_pic = #{avatarUrl}, update_time = now() " +
            "where id = #{idStr}"  )
    void updateAvatar(String avatarUrl, Integer idStr);

    @Update("update user set password = #{password}, update_time = now() " +
            "where id = #{idStr}")
    void updatePwd(String password, Integer idStr);
}
