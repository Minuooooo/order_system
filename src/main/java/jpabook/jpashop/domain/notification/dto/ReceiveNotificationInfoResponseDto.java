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

    public ReceiveNotificationInfoResponseDto from(Notification sentNotification, Member sender, String name) {
        return ReceiveNotificationInfoResponseDto.builder()
                .category(Objects.requireNonNull(Category.nameOf(name)).getName())
                .senderName(sender.getName())
                .content(sentNotification.getContent())
                .build();
    }
}
