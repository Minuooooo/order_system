package jpabook.jpashop.domain.chatmessage.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    ENTER("입장"),
    TALK("대화");

    private final String name;

    public static Type nameOf(String name) {
        for (Type type : Type.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
