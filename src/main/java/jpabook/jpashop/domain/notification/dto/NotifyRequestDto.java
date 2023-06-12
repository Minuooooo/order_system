package jpabook.jpashop.domain.notification.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.notification.entity.Category;
import jpabook.jpashop.domain.notification.entity.Notification;
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
public class NotifyRequestDto {

    @NotBlank(message = "알림 카테고리를 입력해주세요.")
    @Schema(description = "알림 카테고리", defaultValue = "좋아요")
    private String category;
    @NotBlank(message = "알림 내용을 입력해주세요.")
    @Schema(description = "알림 내용", defaultValue = "프로필이 멋지네요~")
    private String content;

    public Notification toEntity(Member receiver, Member sender) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return Notification.builder()
                .receiver(receiver)
                .sender(sender)
                .category(Category.nameOf(this.category))
                .content(this.content)
                .date(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }
}
