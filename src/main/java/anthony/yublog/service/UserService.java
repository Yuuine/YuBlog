package anthony.yublog.service;

import anthony.yublog.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(@URL String avatarUrl);
}
