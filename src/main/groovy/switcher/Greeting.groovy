package switcher

import org.hibernate.validator.constraints.NotBlank

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Created by ngandriau on 2/20/15.
 */
class Greeting {
    @NotNull public long id;
    @NotBlank public String content;
    @Min(10L) public Long num;

    Map<String, String> fields;
}
