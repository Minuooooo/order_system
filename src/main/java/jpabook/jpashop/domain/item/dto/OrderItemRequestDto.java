package jpabook.jpashop.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

    private String name;
    private int price;
    private int stockQuantity;
    private String category;
    private String author;
    private String isbn;
    private String artist;
    private String director;
}
