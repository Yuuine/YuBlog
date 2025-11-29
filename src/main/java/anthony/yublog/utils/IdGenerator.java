package anthony.yublog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    @Autowired
    private UUIDUtil uuidUtil;

    public Long generateId() {
        return uuidUtil.nextId();
    }
}
