package jpabook.jpashop.domain.order.repository;

import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"cutomer"})
    @NotNull
    Page<Order> findOrdersByCustomer(Pageable pageable, Member customer);
}
