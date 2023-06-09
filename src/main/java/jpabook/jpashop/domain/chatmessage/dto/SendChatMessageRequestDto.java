package jpabook.jpashop.domain.chatmessage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
import jpabook.jpashop.domain.chatmessage.entity.Type;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendChatMessageRequestDto {

    @NotBlank(message = "메시지 타입을 입력해주세요.")
    @Schema(description = "메시지 타입")
    private String type;

    @NotBlank(message = "메시지 내용을 입력해주세요")
    @Schema(description = "메시지 내용")
    private String content;

    public ChatMessage toEntity(Member sender, ChatRoom chatRoom) {

        if (this.content.equals("x")) {  // TODO 일단은 클라이언트에게 content 를 x 요청 받을 때 회원이 입장하는 것으로 판단
            this.content = sender.getName() + "님이 입장하였습니다.";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ChatMessage.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .type(Type.nameOf(this.type))
                .content(this.content)
                .time(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }
}