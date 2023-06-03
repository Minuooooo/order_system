package jpabook.jpashop.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditOrderInfoRequestDto {

    @NotBlank(message = "시,도,군을 입력해주세요.")
    @Schema(description = "시,도,군", defaultValue = "서울")
    private String city;

    @NotBlank(message = "도로명을 입력해주세요.")
    @Schema(description = "도로명", defaultValue = "김수로")
    private String street;

    @NotBlank(message = "우편번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{5}$", message = "우편번호는 5자리이어야 합니다.") // 우편번호 형식
    @Schema(description = "우편번호", defaultValue = "11111")
    private String zipcode;
}
