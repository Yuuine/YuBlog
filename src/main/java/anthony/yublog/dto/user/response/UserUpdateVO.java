package anthony.yublog.dto.user.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateVO {
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
}
