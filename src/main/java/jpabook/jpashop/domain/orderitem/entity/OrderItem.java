package jpabook.jpashop.domain.orderitem.entity;

import jpabook.jpashop.domain.EntityDateInfo;
import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OrderItem extends EntityDateInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public void editOrderItem(int count, Item item) {
        this.count = count;
        this.orderPrice = this.count * item.getPrice();
    }
}
