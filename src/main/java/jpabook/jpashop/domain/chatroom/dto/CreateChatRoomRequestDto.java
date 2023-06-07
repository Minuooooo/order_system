package jpabook.jpashop.domain.chatroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChatRoomRequestDto {

    @NotBlank(message = "채팅방 이름을 입력해주세요.")
    @Size(min = 2, message = "채팅방 이름은 두 글자 이상이어야 합니다.")
    @Schema(description = "채팅방 이름", defaultValue = "아무나 들어와~")
    private String name;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .name(this.name)
                .build();
    }
}
