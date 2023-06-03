package jpabook.jpashop.domain.orderitem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditOrderItemInfoRequestDto {
    @NotNull(message = "상품 수량을 입력해주세요.")
    @Min(value = 1, message = "수량은 1개 이상 입력해야 합니다.")
    @Schema(description = "상품 수량")
    private int count;
}
