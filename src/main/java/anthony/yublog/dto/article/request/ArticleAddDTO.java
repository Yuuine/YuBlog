package anthony.yublog.dto.article.request;

import anthony.yublog.enums.ArticleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ArticleAddDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    @URL
    private String coverImg;
    @NotBlank
    private ArticleStatus state;
    @NotNull
    private Integer categoryId;
}
