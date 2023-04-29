package jpabook.jpashop.domain.order.entity;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    READY("배송준비중"),
    ING("배송중"),
    COMPLETE("배송완료");

    private final String name;
    DeliveryStatus(String name) {
        this.name = name;
    }

    public static DeliveryStatus nameOf(String name) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.getName().equals(name)) {
                return deliveryStatus;
            }
        }
        return null;
    }
}
