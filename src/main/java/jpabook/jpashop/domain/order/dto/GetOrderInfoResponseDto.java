package jpabook.jpashop.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOrderInfoResponseDto {

    private Long id;
    private Address address;
    private String date;
    private String orderStatus;
    private String deliveryStatus;

    public static GetOrderInfoResponseDto from(Order order) {
        return GetOrderInfoResponseDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .date(order.getDate().toString())
                .orderStatus(order.getOrderStatus().getName())
                .deliveryStatus(order.getDeliveryStatus().getName())
                .build();
    }
}
