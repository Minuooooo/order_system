package jpabook.jpashop.domain.order.entity;

import jpabook.jpashop.domain.EntityDateInfo;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "`order`")
public class Order extends EntityDateInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    private Address address;
    private LocalDateTime date; //주문시간
    @Enumerated(STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
    @Enumerated(STRING)
    private DeliveryStatus deliveryStatus; // 배송 상태 [READY, ING, COMP]
}
