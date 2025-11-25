package anthony.yublog.service.impl;

import anthony.yublog.exception.BizException;
import anthony.yublog.exception.ErrorCode;
import anthony.yublog.mapper.UserMapper;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import anthony.yublog.utils.BcryptUtil;
import anthony.yublog.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 判断用户名是否存在
     * @param username 用户名
     */
    @Override
    public void existByUserName(String username) {
        boolean exist = userMapper.existByUserName(username);
        if (exist) {
            log.info("用户名已存在 username: {}", username);
            throw new BizException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    /**
     * 根据用户名查询用户
     * @return  用户对象
     * @param username 用户名
     */
    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public void register(String username, String password) {

        //密码加密
        /*
          使用Spring Security 内置支持的加密工具类Bcrypt
          封装bcryptUtil工具类，用于明文密码加密
          使用bcryptUtil的encode方法对密码进行加密
         */
        String bcryptPassword = BcryptUtil.encode(password);
        log.info("用户名: {}", username);
        log.info("生成密文: {}", bcryptPassword);
        userMapper.add(username, bcryptPassword);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Object id = map.get("id");
        Integer idStr = Integer.valueOf(id.toString());
        userMapper.updateAvatar(avatarUrl, idStr);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Object id = map.get("id");
        Integer idStr = Integer.valueOf(id.toString());
        //密码加密
        String bcryptPassword = BcryptUtil.encode(newPwd);
        userMapper.updatePwd(bcryptPassword, idStr);
    }
}
