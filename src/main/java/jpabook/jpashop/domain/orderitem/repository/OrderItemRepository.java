package jpabook.jpashop.domain.orderitem.repository;

import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"item"})
    @NotNull
    Page<OrderItem> findOrderItemsWithItemByOrder(Pageable pageable, Order order);
    @EntityGraph(attributePaths = {"item"})
    List<OrderItem> findOrderItemsWithItemByOrder(Order order);
    List<OrderItem> findOrderItemsByOrder(Order order);
}
