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
@DiscriminatorValue("M")  // DB 에서 객체를 가져올 때 Key 와 같은 역할
public class Movie extends Item {
    private String director;

    @Builder
    public Movie(String name, int price, int stockQuantity, String director) {
        super(name, price, stockQuantity);
        this.director = director;
    }
}
