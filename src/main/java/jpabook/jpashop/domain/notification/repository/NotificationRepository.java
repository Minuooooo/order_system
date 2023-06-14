package jpabook.jpashop.domain.notification.repository;

import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @EntityGraph(attributePaths = {"sender"})
    List<Notification> findNotificationsByReceiver(Member receiver);
}
