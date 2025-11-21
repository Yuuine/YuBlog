package anthony.yublog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryListDTO {

    @NotBlank(message = "分类id不能为空")
    private Integer id;//主键ID
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;//分类名称
    @NotBlank(message = "分类别名不能为空")
    private String categoryAlias;//分类别名
    @NotBlank(message = "创建人id不能为空")
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;//更新时间
}
