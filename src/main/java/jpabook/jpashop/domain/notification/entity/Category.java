package jpabook.jpashop.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    FAVORITE("좋아요");

    private final String name;

    public static Category nameOf(String name) {
        for(Category category : Category.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
