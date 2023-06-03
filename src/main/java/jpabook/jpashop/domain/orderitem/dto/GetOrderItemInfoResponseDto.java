package jpabook.jpashop.domain.orderitem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOrderItemInfoResponseDto {

    private Long itemId;
    private Long orderItemId;
    private String name;
    private int count;
    private int orderPrice;

    public static GetOrderItemInfoResponseDto from(OrderItem orderItem) {
        return GetOrderItemInfoResponseDto.builder()
                .itemId(orderItem.getItem().getId())
                .orderItemId(orderItem.getId())
                .name(orderItem.getItem().getName())
                .count(orderItem.getCount())
                .orderPrice(orderItem.getOrderPrice())
                .build();
    }
}
