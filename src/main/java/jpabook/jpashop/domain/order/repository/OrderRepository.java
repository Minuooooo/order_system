package jpabook.jpashop.domain.order.repository;

import jpabook.jpashop.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
