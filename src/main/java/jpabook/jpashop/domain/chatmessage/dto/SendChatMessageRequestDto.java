package jpabook.jpashop.domain.chatmessage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendChatMessageRequestDto {

    @NotNull(message = "채팅방 id를 입력해주세요")
    @Schema(description = "채팅방 id")
    private Long id;
    @NotBlank(message = "메시지 내용을 입력해주세요")
    @Schema(description = "메시지 내용")
    private String content;

    public ChatMessage toEntity(Member sender, ChatRoom chatRoom) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return ChatMessage.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .content(this.content)
                .time(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }
}
