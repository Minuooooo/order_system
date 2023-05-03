package jpabook.jpashop.domain.item.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;

    public Album(String name, int price, int stockQuantity, String artist) {
        super(name, price, stockQuantity);
        this.artist = artist;
    }
}
