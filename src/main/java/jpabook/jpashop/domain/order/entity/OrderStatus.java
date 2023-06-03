package jpabook.jpashop.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    WAIT("주문대기"),
    COMPLETE("주문완료");

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
