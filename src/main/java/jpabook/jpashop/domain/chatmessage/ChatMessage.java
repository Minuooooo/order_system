package jpabook.jpashop.domain.chatmessage;

import jpabook.jpashop.domain.chatroom.ChatRoom;
import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member sender;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
