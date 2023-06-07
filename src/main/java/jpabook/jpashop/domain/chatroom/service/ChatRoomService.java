package jpabook.jpashop.domain.chatroom.service;

import jpabook.jpashop.domain.chatroom.dto.CreateChatRoomRequestDto;
import jpabook.jpashop.domain.chatroom.dto.EditChatRoomInfoResponseDto;
import jpabook.jpashop.domain.chatroom.dto.GetChatRoomInfoResponseDto;
import jpabook.jpashop.domain.chatroom.entity.ChatRoom;
import jpabook.jpashop.domain.chatroom.repository.ChatRoomRepository;
import jpabook.jpashop.exception.situation.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public void createChatRoom(CreateChatRoomRequestDto createChatRoomRequestDto) {
        chatRoomRepository.save(createChatRoomRequestDto.toEntity());
    }

    public List<GetChatRoomInfoResponseDto> getChatRoomInfos() {
        return chatRoomRepository.findAll().stream()
                .map(GetChatRoomInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public void editChatRoomInfo(EditChatRoomInfoResponseDto editChatRoomInfoResponseDto, Long chatRoomId) {
        getChatRoom(chatRoomId).editChatRoom(editChatRoomInfoResponseDto.getName());
    }

    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.delete(getChatRoom(chatRoomId));
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
    }
}
