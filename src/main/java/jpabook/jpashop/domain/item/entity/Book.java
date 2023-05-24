package jpabook.jpashop.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("B")  // DB 에서 객체를 가져올 때 Key 와 같은 역할
public class Book extends Item {
    private String author;
    private String isbn;

    @Builder
    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
