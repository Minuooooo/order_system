package jpabook.jpashop.domain.item.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoResponseDto {
    private int price;
    private int stockQuantity;
}
