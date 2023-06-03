package jpabook.jpashop.domain.orderitem.service;

import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.dto.EditOrderItemInfoRequestDto;
import jpabook.jpashop.domain.orderitem.dto.GetOrderItemInfoResponseDto;
import jpabook.jpashop.domain.orderitem.dto.PutItemRequestDto;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import jpabook.jpashop.domain.orderitem.repository.OrderItemRepository;
import jpabook.jpashop.exception.situation.OrderItemNotFoundException;
import jpabook.jpashop.exception.situation.StockQuantityExcessException;
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

    public void putItem(PutItemRequestDto putItemRequestDto, Order order, Item item) {
        validateItemCount(putItemRequestDto.getCount(), item.getStockQuantity());
        orderItemRepository.save(putItemRequestDto.toEntity(order, item));
    }

    public Page<GetOrderItemInfoResponseDto> getOrderItemInfos(Pageable pageable, Order order) {

        Page<OrderItem> foundOrderItems = orderItemRepository.findOrderItemsByOrder(pageable, order);

        return new PageImpl<>(foundOrderItems.getContent().stream()
                .map(GetOrderItemInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                foundOrderItems.getTotalElements()
        );
    }

    @Transactional
    public void editOrderItem(EditOrderItemInfoRequestDto editOrderItemRequestDto, Long orderItemId) {
        OrderItem foundOrderItem = getOrderItem(orderItemId);
        foundOrderItem.editOrderItem(editOrderItemRequestDto.getCount(), foundOrderItem.getItem());
    }

    public void cancelOrderItem(Long orderItemId) {
        orderItemRepository.delete(getOrderItem(orderItemId));
    }

    private void validateItemCount(int count, int stockQuantity) {
        if (count > stockQuantity) {
            throw new StockQuantityExcessException();
        }
    }

    private OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(OrderItemNotFoundException::new);
    }
}
