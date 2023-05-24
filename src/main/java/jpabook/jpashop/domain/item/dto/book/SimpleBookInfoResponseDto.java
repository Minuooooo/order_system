package jpabook.jpashop.domain.item.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.dto.item.SimpleItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Book;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleBookInfoResponseDto extends SimpleItemInfoResponseDto {

    private String author;
    private String isbn;

    @Builder
    public SimpleBookInfoResponseDto(Long id, String name, String author, String isbn) {
        super(id, name);
        this.author = author;
        this.isbn = isbn;
    }

    public static SimpleBookInfoResponseDto from(Book book) {
        return SimpleBookInfoResponseDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }
}
