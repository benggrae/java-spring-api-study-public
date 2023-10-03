package com.project.springapistudy.menu.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Builder;


@Builder
public record MenuCreateRequest(
        @Size(min = 1, max = 255)
        String name,

        @NotBlank
        String category,

        @PositiveOrZero // 양수와 0만 허용
        BigDecimal price
) {

}
