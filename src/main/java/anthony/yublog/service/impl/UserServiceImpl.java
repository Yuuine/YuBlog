package anthony.yublog.service.impl;

import anthony.yublog.mapper.UserMapper;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import anthony.yublog.utils.BcryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {

        //密码加密
        /**
         * 使用Spring Security 内置支持的加密工具类Bcrypt
         * 封装bcryptUtil工具类，用于明文密码加密
         * 使用bcryptUtil的encode方法对密码进行加密
         */
        String bcryptPassword = BcryptUtil.encode(password);
        log.info("用户名: {}", username);
        log.info("生成密文: {}", bcryptPassword);
        userMapper.add(username,bcryptPassword);
    }
}
