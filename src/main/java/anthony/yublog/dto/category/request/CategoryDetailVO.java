package anthony.yublog.dto.category.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDetailVO {

    @NotNull
    private Integer id;
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;
    @NotBlank(message = "分类别名不能为空")
    private String categoryAlias;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
