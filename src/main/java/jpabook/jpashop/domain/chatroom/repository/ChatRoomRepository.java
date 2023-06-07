package jpabook.jpashop.domain.chatroom.repository;

import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
