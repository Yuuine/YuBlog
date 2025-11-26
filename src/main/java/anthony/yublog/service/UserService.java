package anthony.yublog.service;

import anthony.yublog.dto.user.request.UserLoginDTO;
import anthony.yublog.dto.user.request.UserUpdateDTO;
import anthony.yublog.dto.user.request.UserUpdatePasDTO;
import anthony.yublog.dto.user.response.UserInfoVO;
import anthony.yublog.dto.user.response.UserUpdateVO;
import anthony.yublog.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {

    boolean existByUserName(String username);

    User getUserByUserName(String username);

    void register(String username, String password);

    String login(UserLoginDTO userLoginDTO);

    UserInfoVO userInfo();

    UserUpdateVO update(UserUpdateDTO userUpdateDTO);

    void updateAvatar(@URL String avatarUrl);

    void updatePwd(UserUpdatePasDTO userUpdatePasDTO);
}
