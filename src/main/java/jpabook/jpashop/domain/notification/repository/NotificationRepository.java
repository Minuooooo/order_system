package jpabook.jpashop.domain.notification.repository;

import jpabook.jpashop.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
