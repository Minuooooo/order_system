package jpabook.jpashop.domain.order.service;

import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


}
