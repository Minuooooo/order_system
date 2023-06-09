package jpabook.jpashop.domain.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.item.service.ItemService;
import jpabook.jpashop.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static jpabook.jpashop.response.Response.success;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Tag(name = "Item", description = "Item API Document")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Get item info API", description = "put the item id what you want to see")
    @ResponseStatus(OK)
    @PageableAsQueryParam
    @GetMapping()
    public Response getSimpleItemInfos(Pageable pageable) {
        return success(SUCCESS_TO_GET_ITEM_INFO, itemService.getItemInfos(pageable));
    }
}
