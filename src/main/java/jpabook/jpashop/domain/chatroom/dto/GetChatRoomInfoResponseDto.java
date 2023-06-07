package jpabook.jpashop.domain.chatroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetChatRoomInfoResponseDto {

    private Long id;
    private String name;

    public static GetChatRoomInfoResponseDto from(ChatRoom chatRoom) {
        return GetChatRoomInfoResponseDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .build();
    }
}
