package anthony.yublog.controller;

import anthony.yublog.dto.user.request.UserLoginDTO;
import anthony.yublog.dto.user.request.UserRegisterDTO;
import anthony.yublog.dto.user.request.UserUpdateDTO;
import anthony.yublog.dto.user.request.UserUpdatePasDTO;
import anthony.yublog.dto.user.response.UserInfoVO;
import anthony.yublog.dto.user.response.UserUpdateVO;
import anthony.yublog.pojo.Result;
import anthony.yublog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Validated  //参数校验框架，Spring提供
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Object> register(UserRegisterDTO userRegisterDTO) {
        //用户注册
        userService.register(userRegisterDTO.getUsername(), userRegisterDTO.getPassword());
        log.info("用户名查询 username: {}", userRegisterDTO.getUsername());
        return Result.success();

    }

    /**
     * 用户登录
     *
     * @return 登录成功返回token，登录失败返回错误信息
     */
    @PostMapping("/login")
    public Result<Object> login(@Validated UserLoginDTO userLoginDTO) {
        //根据用户名查询用户, 判断用户是否存在
        String token = userService.login(userLoginDTO);
        //登录成功，返回 token
        return Result.success(token);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/userInfo")
    public Result<UserInfoVO> userInfo() {
        UserInfoVO userInfoVO = userService.userInfo();
        return Result.success(userInfoVO);
    }

    /**
     * 更新用户信息
     * 1. nickname
     * 2. email
     */

    @PutMapping("/update")
    public Result<UserUpdateVO> update(@RequestBody @Validated UserUpdateDTO userUpdateDTO) {
        UserUpdateVO userUpdateVO = userService.update(userUpdateDTO);
        log.info("用户信息更新成功");
        return Result.success(userUpdateVO);
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
    public Result<Object> updatePwd(@RequestBody @Validated UserUpdatePasDTO userUpdatePasDTO) {
        userService.updatePwd(userUpdatePasDTO);
        return Result.success();
    }

}
