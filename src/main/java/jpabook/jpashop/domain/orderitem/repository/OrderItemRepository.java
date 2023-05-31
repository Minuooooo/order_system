package jpabook.jpashop.domain.orderitem.repository;

import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"item"})
    @NotNull
    List<OrderItem> findOrderItemsByOrder(Order order);
}
