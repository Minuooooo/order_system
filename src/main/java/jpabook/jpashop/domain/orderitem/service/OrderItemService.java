package jpabook.jpashop.domain.orderitem.service;

import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.dto.EditOrderItemRequestDto;
import jpabook.jpashop.domain.orderitem.dto.GetOrderItemInfoResponseDto;
import jpabook.jpashop.domain.orderitem.dto.PutItemRequestDto;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import jpabook.jpashop.domain.orderitem.repository.OrderItemRepository;
import jpabook.jpashop.exception.situation.OrderItemNotFoundException;
import jpabook.jpashop.exception.situation.QuantityExcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public void putItem(PutItemRequestDto putItemRequestDto, Item item) {
        validateItemCount(putItemRequestDto, item.getStockQuantity());
        putItemRequestDto.toEntity(item);
    }

    public Page<GetOrderItemInfoResponseDto> getOrderItemInfos(Order order, Pageable pageable) {

        List<OrderItem> foundOrderItems = orderItemRepository.findOrderItemsByOrder(order);

        return new PageImpl<>(foundOrderItems.stream()
                .map(GetOrderItemInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                foundOrderItems.size());
    }

    @Transactional
    public void editOrderItem(EditOrderItemRequestDto editOrderItemRequestDto, Long orderItemId) {
        OrderItem foundOrderItem = getOrderItem(orderItemId);
        foundOrderItem.editOrderItem(editOrderItemRequestDto.getCount(), foundOrderItem.getItem());
    }

    public void cancelOrderItem(Long orderItemId) {
        orderItemRepository.delete(getOrderItem(orderItemId));
    }

    private void validateItemCount(PutItemRequestDto putItemRequestDto, int stockQuantity) {
        if (putItemRequestDto.getCount() > stockQuantity) {
            throw new QuantityExcessException();
        }
    }

    private OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(OrderItemNotFoundException::new);
    }
}
