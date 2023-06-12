package jpabook.jpashop.domain.notification.controller;

import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.domain.notification.dto.NotifyRequestDto;
import jpabook.jpashop.domain.notification.service.NotificationService;
import jpabook.jpashop.response.Response;
import jpabook.jpashop.response.SuccessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.*;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;

    @ResponseStatus(OK)
    @MessageMapping("/favorite")
    public Response notify(@Valid @RequestBody NotifyRequestDto notifyRequestDto) {
        notificationService.notify(notifyRequestDto, memberService.getMember(notifyRequestDto.getReceiverId()), memberService.getCurrentMember());
        return success(SUCCESS_TO_NOTIFY);
    }
}
