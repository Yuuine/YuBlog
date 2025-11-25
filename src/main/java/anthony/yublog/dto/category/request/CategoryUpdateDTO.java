package anthony.yublog.dto.category.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryUpdateDTO {
    @NotNull
    private Integer id;//主键ID
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;//分类名称
    @NotBlank(message = "分类别名不能为空")
    private String categoryAlias;//分类别名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
