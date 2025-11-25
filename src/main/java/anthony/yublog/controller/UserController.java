package anthony.yublog.controller;

import anthony.yublog.dto.user.request.UserLoginDTO;
import anthony.yublog.dto.user.request.UserRegisterDTO;
import anthony.yublog.pojo.Result;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import anthony.yublog.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static anthony.yublog.utils.BcryptUtil.matches;


@Slf4j
@Validated  //参数校验框架，Spring提供
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Object> register(UserRegisterDTO userRegisterDTO) {
        //用户注册
        userService.register(userRegisterDTO.getUsername(), userRegisterDTO.getPassword());
        log.info("用户名查询 username: {}", userRegisterDTO.getUsername());
        return Result.success();

    }

    /**
     * 用户注册
     * @return 登录成功返回token，登录失败返回错误信息
     */
    @PostMapping("/login")
    public Result<Object> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        //根据用户名查询用户, 判断用户是否存在
        String token = userService.login(userLoginDTO);
        //登录成功，返回 token
        return Result.success(token);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        User  user = userService.userInfo();
        return Result.success(user);
    }

    /**
     * 更新用户信息
     * 1. nickname
     * 2. email
     */

    @PutMapping("/update")
    public Result<Object> update(@RequestBody @Validated User user) {
        userService.update(user);
        log.info("用户信息更新成功");
        return Result.success(user);
    }

    /**
     * 更新用户头像
     */

    @PatchMapping("/updateAvatar")
    public Result<Object> updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        log.info("用户更新头像成功");
        return Result.success();
    }

    /*
      更新用户密码
     */

    @PatchMapping("/updatePwd")
    public Result<Object> updatePwd(@RequestBody Map<String, String> params) {

        //1. 校验传过来的密码的参数，防止不合法的密码
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        //2. 判断旧密码是否正确，不正确不允许修改密码
        //使用userService通过用户名校验用户输入的旧密码是否和数据库的密码一致
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.getUserByUserName(username);
        if (!matches(oldPwd, user.getPassword())) {
            log.info("用户 {} 旧密码错误", username);
            return Result.error("旧密码错误");
        }

        //3. 新密码和确认密码是否一致，不一致不允许修改密码
        if (!newPwd.equals(rePwd)) {
            log.info("用户 {} 新密码和确认密码不一致", username);
            return Result.error("新密码和确认密码不一致");
        }

        //4. 调用service完成密码修改
        userService.updatePwd(newPwd);
        log.info("用户 {} 修改密码成功", username);
        return Result.success();
    }

}
