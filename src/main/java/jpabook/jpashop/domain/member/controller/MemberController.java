package jpabook.jpashop.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.member.dto.member.EditMemberInfoRequestDto;
import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.success;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "Member", description = "Member API Document")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "Edit member API", description = "put profile info what you want to change")
    @ResponseStatus(OK)
    @PatchMapping("")
    public Response editMemberInfo(@Valid @RequestBody EditMemberInfoRequestDto editMemberInfoRequestDto) {
        memberService.editMemberInfo(editMemberInfoRequestDto);
        return success(SUCCESS_TO_EDIT_MEMBER);
    }

    @Operation(summary = "Delete member API", description = "this is to delete member")
    @ResponseStatus(OK)
    @DeleteMapping("")
    public Response deleteMember() {
        memberService.deleteMember();
        return success(SUCCESS_TO_DELETE_MEMBER);
    }

    @Operation(summary = "Change member profile image to new API", description = "put profile image what you want to change")
    @ResponseStatus(OK)
    @PostMapping(value = "/profile-image-new", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public Response changeProfileImageToNew(@RequestPart MultipartFile profileImage) {
        return success(SUCCESS_TO_CHANGE_MEMBER_PROFILE_IMAGE, memberService.changeProfileImageToNew(profileImage));
    }

    @Operation(summary = "Change member profile image to basic API", description = "this is to change profile image to basic")
    @ResponseStatus(OK)
    @PostMapping("/profile-image-basic")
    public Response changeLogoImageToBasic() {
        memberService.changeProfileImageToBasic();
        return success(SUCCESS_TO_CHANGE_MEMBER_PROFILE_IMAGE);
    }
}
