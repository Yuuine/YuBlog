package anthony.yublog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {
    @NotNull
    private Integer id;
    private String username;
    @NotBlank
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @Email
    private String email;
    private String userPic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
