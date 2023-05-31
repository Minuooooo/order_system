package jpabook.jpashop.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER("주문완료"),
    CANCEL("주문취소");

    private final String name;

    public static OrderStatus nameOf(String name) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getName().equals(name)) {
                return orderStatus;
            }
        }
        return null;
    }
}
