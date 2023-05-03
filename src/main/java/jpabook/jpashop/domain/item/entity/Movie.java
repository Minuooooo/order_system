package jpabook.jpashop.domain.item.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;

    public Movie(String name, int price, int stockQuantity, String director) {
        super(name, price, stockQuantity);
        this.director = director;
    }
}
