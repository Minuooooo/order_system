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
@DiscriminatorValue("A")  // DB 에서 객체를 가져올 때 Key 와 같은 역할
public class Album extends Item {
    private String artist;

    @Builder
    public Album(String name, int price, int stockQuantity, String artist) {
        super(name, price, stockQuantity);
        this.artist = artist;
    }
}
