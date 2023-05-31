package jpabook.jpashop.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.dto.GetOrderItemInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOrderInfosResponseDto {

    private Long id;
    private Address address;
    private String date;
    private String orderStatus;
    private String deliveryStatus;

    public static GetOrderInfosResponseDto from(Order order) {
        return GetOrderInfosResponseDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .date(order.getDate().toString())
                .orderStatus(order.getOrderStatus().getName())
                .deliveryStatus(order.getDeliveryStatus().getName())
                .build();
    }
}
