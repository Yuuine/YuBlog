package anthony.yublog.controller;

import anthony.yublog.pojo.Result;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import anthony.yublog.utils.JwtUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static anthony.yublog.utils.BcryptUtil.matches;


@Slf4j
@Validated  //参数校验框架，Spring提供
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Object> register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        //查询用户
        User user = userService.findByUserName(username);
        log.info("用户名查询 username: {}", username);
        //判断用户名是否存在
        if (user == null) {
            //用户名不存在
            userService.register(username, password);
            return Result.success();
        } else {
            //用户名已存在
            log.info("用户名已存在 username: {}", username);
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<Object> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
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
}
