package jpabook.jpashop.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.notification.entity.Notification;
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
public class GetNotificationInfoResponseDto {

    private Long id;
    private String senderName;
    private String category;
    private String content;
    private String date;

    public static GetNotificationInfoResponseDto from(Notification notification) {
        return GetNotificationInfoResponseDto.builder()
                .id(notification.getId())
                .senderName(notification.getSender().getName())
                .category(notification.getCategory().getName())
                .content(notification.getContent())
                .date(notification.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
