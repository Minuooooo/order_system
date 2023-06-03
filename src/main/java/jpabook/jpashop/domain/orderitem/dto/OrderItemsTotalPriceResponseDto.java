package jpabook.jpashop.domain.orderitem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemsTotalPriceResponseDto {
    private int totalPrice;

    public static OrderItemsTotalPriceResponseDto from(int totalPrice) {
        return OrderItemsTotalPriceResponseDto.builder()
                .totalPrice(totalPrice)
                .build();
    }
}
