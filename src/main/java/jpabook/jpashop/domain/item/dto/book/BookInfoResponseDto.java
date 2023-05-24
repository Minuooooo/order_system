package jpabook.jpashop.domain.item.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.dto.item.ItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Book;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookInfoResponseDto extends ItemInfoResponseDto {

    SimpleBookInfoResponseDto simpleBook;  // TODO 데이터를 simpleBook 으로 감싸므로 고민 필요

    @Builder
    public BookInfoResponseDto(int price, int stockQuantity, SimpleBookInfoResponseDto simpleBook) {
        super(price, stockQuantity);
        this.simpleBook = simpleBook;
    }

    public static BookInfoResponseDto from(Book book) {
        return BookInfoResponseDto.builder()
                .simpleBook(SimpleBookInfoResponseDto.from(book))
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .build();
    }
}
