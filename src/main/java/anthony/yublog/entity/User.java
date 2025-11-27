package anthony.yublog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//@Data lombok注解，自动生成getter,setter,toString等方法
public class User {
    @NotNull
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @Email
    private String email;
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
