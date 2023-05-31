package jpabook.jpashop.domain.orderitem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.orderitem.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutItemRequestDto {

    @NotBlank(message = "상품 수량을 입력해주세요.")
    @Size(min = 1, message = "상품을 1개 이상 담아주세요.")
    @Schema(description = "상품 수량")
    private int count;

    public OrderItem toEntity(Item item) {
        return OrderItem.builder()
                .count(this.count)
                .orderPrice(this.count * item.getPrice())
                .build();
    }
}
