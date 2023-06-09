package jpabook.jpashop.domain.chatmessage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.chatmessage.dto.SendChatMessageRequestDto;
import jpabook.jpashop.domain.chatmessage.service.ChatMessageService;
import jpabook.jpashop.domain.chatroom.dto.CreateChatRoomRequestDto;
import jpabook.jpashop.domain.chatroom.service.ChatRoomService;
import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.success;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-messages")
@Tag(name = "ChatMessage", description = "ChatMessage API Document")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    @ResponseStatus(OK)
    @MessageMapping("/chat-messages")
    public Response sendChatMessage(@Valid @RequestBody SendChatMessageRequestDto sendChatMessageRequestDto, Long chatRoomId) {
        chatMessageService.sendChatMessage(sendChatMessageRequestDto, memberService.getCurrentMember(), chatRoomService.getChatRoom(chatRoomId));
        return success(SUCCESS_TO_SEND_CHAT_MESSAGE);
    }

    @Operation(summary = "Delete chat message API", description = "please chat message id what you want to delete")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deleteChatMessage(Long chatMessageId) {
        chatMessageService.deleteChatMessage(chatMessageId);
        return success(SUCCESS_TO_DELETE_CHAT_MESSAGE);
    }
}
