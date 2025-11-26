package anthony.yublog.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdatePasDTO {
    @NotBlank(message = "旧密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    @JsonProperty("old_pwd")
    private String oldPwd;
    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    @JsonProperty("new_pwd")
    private String newPwd;
    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$")
    @JsonProperty("re_pwd")
    private String rePwd;
}
