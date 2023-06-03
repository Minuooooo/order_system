package jpabook.jpashop.domain.orderitem.service;

import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.item.service.ItemService;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.dto.EditOrderItemInfoRequestDto;
import jpabook.jpashop.domain.orderitem.dto.GetOrderItemInfoResponseDto;
import jpabook.jpashop.domain.orderitem.dto.OrderItemsTotalPriceResponseDto;
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
    private final ItemService itemService;  // TODO OrderController 에서 호출하면 Controller layer 에서 서비스 로직을 구현해야 해서 고민 필요

    public OrderItemsTotalPriceResponseDto putItem(PutItemRequestDto putItemRequestDto, Order order, Item item) {
        validateItemCount(putItemRequestDto.getCount(), item.getStockQuantity());
        orderItemRepository.save(putItemRequestDto.toEntity(order, item));

        List<OrderItem> foundOrderItems = orderItemRepository.findOrderItemsByOrder(order);
        int totalPrice = 0;
        for (OrderItem orderItem : foundOrderItems) {
            totalPrice += orderItem.getOrderPrice();
        }
        return OrderItemsTotalPriceResponseDto.from(totalPrice);
    }

    public Page<GetOrderItemInfoResponseDto> getOrderItemInfos(Pageable pageable, Order order) {

        Page<OrderItem> foundOrderItems = orderItemRepository.findOrderItemsWithItemByOrder(pageable, order);

        return new PageImpl<>(foundOrderItems.getContent().stream()
                .map(GetOrderItemInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                foundOrderItems.getTotalElements()
        );
    }

    @Transactional
    public void editOrderItemInfo(EditOrderItemInfoRequestDto editOrderItemRequestDto, Long orderItemId, Item item) {
        validateItemCount(editOrderItemRequestDto.getCount(), item.getStockQuantity());
        getOrderItem(orderItemId).editOrderItem(editOrderItemRequestDto.getCount(), item.getPrice());
    }

    public void cancelOrderItem(Long orderItemId) {
        orderItemRepository.delete(getOrderItem(orderItemId));
    }

    public void putItemStockQuantity(Order order) {
        List<OrderItem> foundOrderItems = orderItemRepository.findOrderItemsWithItemByOrder(order);
        for (OrderItem orderItem : foundOrderItems) {
            itemService.putItem(orderItem.getItem().getId(), orderItem.getCount());
        }
    }

    private void validateItemCount(int count, int stockQuantity) {  // 요청한 주문 물품 개수와 물품 재고 수량 비교
        if (count > stockQuantity) {
            throw new StockQuantityExcessException();
        }
    }

    private OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(OrderItemNotFoundException::new);
    }
}
