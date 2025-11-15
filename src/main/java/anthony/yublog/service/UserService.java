package anthony.yublog.service;

import anthony.yublog.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);
}
