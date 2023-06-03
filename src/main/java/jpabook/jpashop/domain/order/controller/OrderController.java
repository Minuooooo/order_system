package jpabook.jpashop.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.domain.order.dto.CreateOrderInfoRequestDto;
import jpabook.jpashop.domain.order.dto.EditOrderInfoRequestDto;
import jpabook.jpashop.domain.order.service.OrderService;
import jpabook.jpashop.domain.orderitem.service.OrderItemService;
import jpabook.jpashop.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.*;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order API Document")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final OrderItemService orderItemService;

    @Operation(summary = "Create order info API", description = "put the order info what you want to create")
    @ResponseStatus(OK)
    @PostMapping()
    public Response createOrderInfo(@Valid @RequestBody CreateOrderInfoRequestDto createOrderInfoRequestDto) {
        orderService.createOrderInfo(createOrderInfoRequestDto, memberService.getCurrentMember());
        return success(SUCCESS_TO_CREATE_ORDER_INFO);
    }

    @Operation(summary = "Get order infos API", description = "put the page info what you want to see")
    @ResponseStatus(OK)
    @PageableAsQueryParam
    @GetMapping()
    public Response getOrderInfos(Pageable pageable) {
        return success(SUCCESS_TO_GET_ORDER_INFOS, orderService.getOrderInfos(pageable, memberService.getCurrentMember()));
    }

    @Operation(summary = "Edit order info API", description = "put the order info and id what you want to edit")
    @ResponseStatus(OK)
    @PatchMapping()
    public Response editOrderInfo(@Valid @RequestBody EditOrderInfoRequestDto editOrderInfoRequestDto, Long orderId) {
        orderService.editOrderInfo(editOrderInfoRequestDto, orderId);
        return success(SUCCESS_TO_EDIT_ORDER_INFO);
    }

    @Operation(summary = "Order completely API", description = "put the order id what you want to order completely")
    @ResponseStatus(OK)
    @PatchMapping("/complete")
    public Response order(Long orderId) {
        orderItemService.putItemStockQuantity(orderService.getOrder(orderId));
        orderService.changeOrderStatusToComplete(orderId);
        return success(SUCCESS_TO_ORDER_COMPLETELY);
    }
}