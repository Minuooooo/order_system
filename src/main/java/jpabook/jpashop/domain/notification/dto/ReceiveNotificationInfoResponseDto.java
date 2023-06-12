package jpabook.jpashop.domain.notification.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.notification.entity.Category;
import jpabook.jpashop.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiveNotificationInfoResponseDto {

    private String category;
    private String senderName;
    private String content;
    private String date;

    public static ReceiveNotificationInfoResponseDto from(Notification sentNotification, String senderName, String name) {  // sender 필드가 하나만 쓰이므로 파라미터로 바로 이름을 받음
        return ReceiveNotificationInfoResponseDto.builder()
                .category(Objects.requireNonNull(Category.nameOf(name)).getName())
                .senderName(senderName)
                .content(sentNotification.getContent())
                .date(sentNotification.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
