package jpabook.jpashop.domain.orderitem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.item.service.ItemService;
import jpabook.jpashop.domain.order.service.OrderService;
import jpabook.jpashop.domain.orderitem.dto.EditOrderItemInfoRequestDto;
import jpabook.jpashop.domain.orderitem.dto.PutItemRequestDto;
import jpabook.jpashop.domain.orderitem.service.OrderItemService;
import jpabook.jpashop.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.success;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orderItems")
@Tag(name = "OrderItem", description = "Order Item API Document")
@Slf4j
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ItemService itemService;

    @Operation(summary = "Put item API", description = "put the purchase info and order id and item id what you want to put")
    @ResponseStatus(OK)
    @PostMapping()
    public Response putItem(@Valid @RequestBody PutItemRequestDto putItemRequestDto, Long orderId, Long itemId) {
        return success(SUCCESS_TO_PUT_ITEM, orderItemService.putItem(putItemRequestDto, orderService.getOrder(orderId), itemService.getItem(itemId)));
    }

    @Operation(summary = "Get order item infos API", description = "put the page info and order id what you want to see")
    @ResponseStatus(OK)
    @PageableAsQueryParam  // Swagger 에서 페이징 정보를 입력 받기 위해
    @GetMapping()
    public Response getOrderItemInfos(Pageable pageable, Long orderId) {
        return success(SUCCESS_TO_GET_ORDER_ITEM_INFOS, orderItemService.getOrderItemInfos(pageable, orderService.getOrder(orderId)));
    }

    @Operation(summary = "Edit order item info API", description = "put the order item id what you want to edit")
    @ResponseStatus(OK)
    @PatchMapping()
    public Response editOrderItemInfo(@Valid @RequestBody EditOrderItemInfoRequestDto editOrderItemInfoRequestDto, Long orderItemId, Long itemId) {
        orderItemService.editOrderItemInfo(editOrderItemInfoRequestDto, orderItemId, itemService.getItem(itemId));
        return success(SUCCESS_TO_EDIT_ORDER_ITEM_INFO);
    }

    @Operation(summary = "Cancel order item API", description = "put the order item id what you want to cancel")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response cancelOrderItem(Long orderItemId) {
        orderItemService.cancelOrderItem(orderItemId);
        return success(SUCCESS_TO_CANCEL_ORDER_ITEM);
    }
}
