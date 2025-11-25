package anthony.yublog.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @NotBlank
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;
}
