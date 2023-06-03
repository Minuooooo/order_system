package jpabook.jpashop.domain.orderitem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutItemRequestDto {

    @NotNull(message = "상품 수량을 입력해주세요.")  // Integer 타입에는 @NotBlank 적용 불가
    @Min(value = 1, message = "수량은 1개 이상 입력해야 합니다.")
    @Schema(description = "상품 수량")
    private int count;

    public OrderItem toEntity(Order order, Item item) {
        return OrderItem.builder()
                .order(order)
                .item(item)
                .count(this.count)
                .orderPrice(this.count * item.getPrice())
                .build();
    }
}
