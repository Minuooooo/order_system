package jpabook.jpashop.domain.chatroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetChatMessageInfoResponseDto {

    private Long id;
    private String senderName;
    private String profileImageUrl;
    private String content;
    private String time;

    public static GetChatMessageInfoResponseDto from(ChatMessage chatMessage) {

        Member sender = chatMessage.getSender();

        return GetChatMessageInfoResponseDto.builder()
                .id(sender.getId())
                .senderName(sender.getName())
                .profileImageUrl(sender.getProfileImageUrl())
                .content(chatMessage.getContent())
                .time(chatMessage.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
