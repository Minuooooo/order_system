package jpabook.jpashop.domain.chatmessage.service;

import jpabook.jpashop.domain.chatmessage.dto.ReceiveChatMessageInfoResponseDto;
import jpabook.jpashop.domain.chatmessage.dto.SendChatMessageRequestDto;
import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
import jpabook.jpashop.domain.chatmessage.repository.ChatMessageRepository;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.exception.situation.ChatMessageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void sendChatMessage(SendChatMessageRequestDto sendChatMessageRequestDto, Member sender, ChatRoom chatRoom) {
        ChatMessage sentChatMessage = sendChatMessageRequestDto.toEntity(sender, chatRoom);
        simpMessageSendingOperations.convertAndSend("/sub/chat-rooms/" + chatRoom.getId(), ReceiveChatMessageInfoResponseDto.from(sentChatMessage));
        chatMessageRepository.save(sentChatMessage);
    }

    public List<ChatMessage> getChatMessages(ChatRoom chatRoom) {
        return chatMessageRepository.findChatMessagesByChatRoom(chatRoom);
    }

    public void deleteChatMessage(Long chatMessageId) {
        chatMessageRepository.delete(getChatMessage(chatMessageId));
    }

    private ChatMessage getChatMessage(Long chatMessageId) {
        return chatMessageRepository.findById(chatMessageId).orElseThrow(ChatMessageNotFoundException::new);
    }
}
