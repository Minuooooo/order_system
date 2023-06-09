package jpabook.jpashop.domain.chatmessage.repository;

import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @EntityGraph(attributePaths = {"sender"})
    List<ChatMessage> findChatMessagesByChatRoom(ChatRoom chatRoom);  // TODO RDB 에서 메시지 내용을 불러오는 것은 성능 이슈가 발생하므로 NoSQL 사용 검토
}
