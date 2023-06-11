package jpabook.jpashop.domain.chatmessage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
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
public class GetSentMessageInfoResponseDto {

    private Long id;
    private String content;
    private String time;

    public static GetSentMessageInfoResponseDto from(ChatMessage sentChatMessage) {
        return GetSentMessageInfoResponseDto.builder()
                .id(sentChatMessage.getId())
                .content(sentChatMessage.getContent())
                .time(sentChatMessage.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
