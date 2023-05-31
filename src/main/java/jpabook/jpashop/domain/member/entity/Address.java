package jpabook.jpashop.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public static Address getAddress(String city, String street, String zipcode) {
        return Address.builder()
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();
    }
}
