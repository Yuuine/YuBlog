package anthony.yublog.service.impl;

import anthony.yublog.dto.user.request.UserLoginDTO;
import anthony.yublog.dto.user.request.UserUpdateDTO;
import anthony.yublog.dto.user.request.UserUpdatePasDTO;
import anthony.yublog.dto.user.response.UserInfoVO;
import anthony.yublog.dto.user.response.UserUpdateVO;
import anthony.yublog.exception.BizException;
import anthony.yublog.exception.ErrorCode;
import anthony.yublog.mapper.UserMapper;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import anthony.yublog.utils.BcryptUtil;
import anthony.yublog.utils.JwtUtil;
import anthony.yublog.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 用户名存在返回 true，不存在返回 false
     */
    @Override
    public boolean existByUserName(String username) {
        return userMapper.existByUserName(username);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public User getUserByUserName(String username) {
        User user = userMapper.getUserByUserName(username);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void register(String username, String password) {
        if (existByUserName(username)) {
            throw new BizException(ErrorCode.USER_ALREADY_EXISTS);
        }
        //密码加密
        /*
          使用Spring Security 内置支持的加密工具类Bcrypt
          封装bcryptUtil工具类，用于明文密码加密
          使用bcryptUtil的encode方法对密码进行加密
         */
        String bcryptPassword = BcryptUtil.encode(password);
        userMapper.add(username, bcryptPassword);
    }

    /**
     * 用户登录
     *
     * @return token 登录成功返回token
     */
    public String login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        User loginUser = getUserByUserName(username);
        //根据用户名查询用户是否存在
        if (loginUser == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        //用户密码验证
        if (!BcryptUtil.matches(password, loginUser.getPassword())) {
            throw new BizException(ErrorCode.USER_OR_PASSWORD_ERROR);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", username);
        return JwtUtil.genToken(claims);
    }

    @Override
    public UserInfoVO userInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = getUserByUserName(username);
        UserInfoVO userInfoVO = new UserInfoVO();
        // 自动复制匹配字段（忽略 password）
        BeanUtils.copyProperties(user, userInfoVO);
        return userInfoVO;
    }

    @Override
    public UserUpdateVO update(UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.setUpdateTime(LocalDateTime.now());
        userMapper.update(userUpdateDTO);
        UserUpdateVO userUpdateVO = new UserUpdateVO();
        BeanUtils.copyProperties(userUpdateDTO, userUpdateVO);
        return userUpdateVO;
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Object id = map.get("id");
        Integer idStr = Integer.valueOf(id.toString());
        userMapper.updateAvatar(avatarUrl, idStr);
    }

    @Override
    public void updatePwd(UserUpdatePasDTO userUpdatePasDTO) {
        String newPwd = userUpdatePasDTO.getNewPwd();
        String oldPwd = userUpdatePasDTO.getOldPwd();
        String rePwd = userUpdatePasDTO.getRePwd();
        Map<String, Object> claim = ThreadLocalUtil.get();
        //判断旧密码是否正确
        String username = (String) claim.get("username");
        String originalPassword = userMapper.getPasswordByUserName(username);
        if (!BcryptUtil.matches(oldPwd, originalPassword)) {
            throw new BizException(ErrorCode.USER_OR_PASSWORD_ERROR);
        }
        //判断新密码是否一致
        if (!newPwd.equals(rePwd)) {
            throw new BizException(ErrorCode.PASSWORDS_NOT_MATCH);
        }
        if (newPwd.equals(oldPwd)) {
            throw new BizException(ErrorCode.NEW_PASSWORD_SAME_AS_OLD);
        }
        //校验通过，新密码加密, 更新密码
        String bcryptNewPassword = BcryptUtil.encode(newPwd);
        Integer userId = Integer.valueOf(claim.get("id").toString());
        userMapper.updatePwd(bcryptNewPassword, userId);
    }
}
