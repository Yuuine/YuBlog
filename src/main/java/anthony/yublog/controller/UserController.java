package anthony.yublog.controller;

import anthony.yublog.pojo.Result;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import anthony.yublog.utils.JwtUtil;
import anthony.yublog.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Result<Object> register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        //查询用户名是否存在
        userService.existByUserName(username);
        //用户注册
        userService.register(username, password);
        log.info("用户名查询 username: {}", username);
        return Result.success();

    }

    @PostMapping("/login")
    public Result<Object> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.getUserByUserName(username);
        //判断用户名是否存在
        //如果不存在，返回错误信息
        if (loginUser == null) {
            return Result.error("用户名或密码错误");
        }
        //判断密码是否正确，如果错误，返回密码错误。如果正确，登录成功
        if (matches(password, loginUser.getPassword())) {

            //生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            log.info("用户 {} 登录成功，生成token: {}", username, token);
            return Result.success(token);

        }
        log.info("用户 {} 登录失败", username);
        return Result.error("用户名或密码错误");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.getUserByUserName(username);
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
