package jpabook.jpashop.domain.order.service;

import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.order.dto.EditOrderInfoRequestDto;
import jpabook.jpashop.domain.order.dto.GetOrderInfoResponseDto;
import jpabook.jpashop.domain.order.dto.CreateOrderInfoRequestDto;
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

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrderInfo(CreateOrderInfoRequestDto createOrderInfoRequestDto, Member customer) {
        orderRepository.save(createOrderInfoRequestDto.toEntity(customer));
    }

    public Page<GetOrderInfoResponseDto> getOrderInfos(Pageable pageable, Member customer) {

        Page<Order> foundOrders = orderRepository.findOrdersByCustomer(pageable, customer);

        return new PageImpl<>(foundOrders.getContent().stream()
                .map(GetOrderInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                foundOrders.getTotalElements()
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

    @Transactional
    public void changeOrderStatusToComplete(Long orderId) {
        getOrder(orderId).changeOrderStatus();
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }
}
