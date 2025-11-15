package anthony.yublog.controller;

import anthony.yublog.pojo.Result;
import anthony.yublog.pojo.User;
import anthony.yublog.service.UserService;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Validated  //参数校验框架，Spring提供
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result<Object> register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){

        //查询用户
        User user = userService.findByUserName(username);
        log.info("用户名查询 username: {}", username);
        //判断用户名是否存在
        if(user == null){
            //用户名不存在
            userService.register(username,password);
            return Result.success();
        } else {
            //用户名已存在
            log.info("用户名已存在 username: {}", username);
            return Result.error("用户名已存在");
        }
    }
}
