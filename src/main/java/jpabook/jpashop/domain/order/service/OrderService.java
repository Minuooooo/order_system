package jpabook.jpashop.domain.order.service;

import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.order.dto.EditOrderInfoRequestDto;
import jpabook.jpashop.domain.order.dto.GetOrderInfosResponseDto;
import jpabook.jpashop.domain.order.dto.OrderRequestDto;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.order.repository.OrderRepository;
import jpabook.jpashop.exception.situation.CannotEditOrderInfoException;
import jpabook.jpashop.exception.situation.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void order(OrderRequestDto orderRequestDto, Member customer) {
        orderRepository.save(orderRequestDto.toEntity(customer));
    }

    public Page<GetOrderInfosResponseDto> getOrderInfos(Member customer, Pageable pageable) {

        List<Order> foundOrders = orderRepository.findOrdersByCustomer(customer);

        return new PageImpl<>(foundOrders.stream()
                .map(GetOrderInfosResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                foundOrders.size()
        );
    }

    @Transactional
    public void editOrderInfo(EditOrderInfoRequestDto editOrderInfoRequestDto, Long orderId) {

        Order foundOrder = getOrder(orderId);

        if (foundOrder.getDeliveryStatus().getName().equals("배송준비중")) {
            foundOrder.editOrder(
                    Address.getAddress(editOrderInfoRequestDto.getCity(),
                            editOrderInfoRequestDto.getStreet(),
                            editOrderInfoRequestDto.getZipcode()
                    )
            );
        } else {
            throw new CannotEditOrderInfoException();
        }
    }

    public void deleteOrder(Long orderId) {
        orderRepository.delete(getOrder(orderId));
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }
}
