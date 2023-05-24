package jpabook.jpashop.domain.item.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleItemInfoResponseDto {
    private Long id;
    private String name;
}
