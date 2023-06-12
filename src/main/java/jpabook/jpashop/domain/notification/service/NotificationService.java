package jpabook.jpashop.domain.notification.service;

import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.notification.dto.NotifyRequestDto;
import jpabook.jpashop.domain.notification.dto.ReceiveNotificationInfoResponseDto;
import jpabook.jpashop.domain.notification.entity.Notification;
import jpabook.jpashop.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void notify(NotifyRequestDto notifyRequestDto, Member receiver, Member sender) {
        Notification sentNotification = notifyRequestDto.toEntity(receiver, sender);
        simpMessageSendingOperations.convertAndSend("/sub/favorite/" + receiver.getId(),
                ReceiveNotificationInfoResponseDto.from(sentNotification, sender.getName(), notifyRequestDto.getCategory())
        );
        notificationRepository.save(sentNotification);
    }

    public List<Notification> getNotificationInfos() {
        return notificationRepository.findAll();
    }
}
