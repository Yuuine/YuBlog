package anthony.yublog.service;

import anthony.yublog.dto.user.request.UserLoginDTO;
import anthony.yublog.dto.user.response.UserInfoVO;
import anthony.yublog.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {

    boolean existByUserName(String username);

    User getUserByUserName(String username);

    void register(String username, String password);

    String login(UserLoginDTO userLoginDTO);

    UserInfoVO userInfo();

    void update(User user);

    void updateAvatar(@URL String avatarUrl);

    void updatePwd(String newPwd);
}
