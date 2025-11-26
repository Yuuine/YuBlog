package anthony.yublog.dto.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUpdateDTO {
    @NotNull
    private Integer id;
    @NotBlank
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @NotBlank
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @Email
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
