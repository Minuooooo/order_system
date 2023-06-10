package jpabook.jpashop.domain.chatroom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.chatmessage.service.ChatMessageService;
import jpabook.jpashop.domain.chatroom.dto.CreateChatRoomRequestDto;
import jpabook.jpashop.domain.chatroom.dto.EditChatRoomInfoResponseDto;
import jpabook.jpashop.domain.chatroom.service.ChatRoomService;
import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.response.Response;
import jpabook.jpashop.response.SuccessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.success;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
@Tag(name = "ChatRoom", description = "ChatRoom API Document")
@Slf4j
public class ChatRoomController {  // TODO 채팅방 나가기

    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final ChatMessageService chatMessageService;

    @Operation(summary = "Create chat room API", description = "please chat room info what you want to create")
    @ResponseStatus(OK)
    @PostMapping()
    public Response createChatRoom(@Valid @RequestBody CreateChatRoomRequestDto createChatRoomRequestDto) {
        chatRoomService.createChatRoom(createChatRoomRequestDto);
        return success(SUCCESS_TO_CREATE_CHAT_ROOM);
    }

    @Operation(summary = "Get chat room infos API", description = "this is to get chat room infos")
    @ResponseStatus(OK)
    @GetMapping("/simple")
    public Response getChatRoomInfos() {
        return success(SUCCESS_TO_GET_CHAT_ROOM_INFOS, chatRoomService.getChatRoomInfos());
    }

    @Operation(summary = "Get chat room info API", description = "please chat room id what you want to see")
    @ResponseStatus(OK)
    @GetMapping("/details")
    public Response getChatRoomInfo(Long chatRoomId) {
        return success(SUCCESS_TO_GET_CHAT_ROOM_INFO, chatRoomService.getChatRoomInfo(chatMessageService.getChatMessageInfos(chatRoomService.getChatRoom(chatRoomId))));
    }

    @Operation(summary = "Enter chat room API", description = "please chat room id what you want to enter")
    @ResponseStatus(OK)
    @GetMapping()
    public Response enterChatRoom(Long chatRoomId) {  // TODO chatRoomId 활용
        return success(memberService.getCurrentMember().getName() + "님이 " + chatRoomId + "번 " + SUCCESS_TO_ENTER_CHAT_ROOM);
    }

    @Operation(summary = "Edit chat room info API", description = "please chat room info and id what you want to edit")
    @ResponseStatus(OK)
    @PatchMapping()
    public Response editChatRoomInfo(@Valid @RequestBody EditChatRoomInfoResponseDto editChatRoomInfoResponseDto, Long chatRoomId) {
        chatRoomService.editChatRoomInfo(editChatRoomInfoResponseDto, chatRoomId);
        return success(SUCCESS_TO_EDIT_CHAT_ROOM_INFO);
    }

    @Operation(summary = "Delete chat room API", description = "please chat room id what you want to delete")
    @ResponseStatus(OK)
    @DeleteMapping()
    public Response deleteChatRoom(Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return success(SUCCESS_TO_DELETE_CHAT_ROOM);
    }
}
