package jpabook.jpashop.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemInfoResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public static ItemInfoResponseDto from(Item item) {
        return ItemInfoResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .build();
    }
}
