package jpabook.jpashop.domain.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    BOOK("도서"),
    ALBUM("음반"),
    MOVIE("영화");

    private final String name;

    public static Category nameOf(String name) {
        for (Category category : Category.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
