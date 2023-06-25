package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.config.aws.AmazonS3Service;
import jpabook.jpashop.config.redis.RedisService;
import jpabook.jpashop.domain.member.dto.member.EditMemberInfoRequestDto;
import jpabook.jpashop.domain.member.dto.member.GetNotificationInfoResponseDto;
import jpabook.jpashop.domain.member.dto.member.GetMemberInfoResponseDto;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.domain.notification.entity.Notification;
import jpabook.jpashop.exception.situation.AlreadyBasicException;
import jpabook.jpashop.exception.situation.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final AmazonS3Service amazonS3Service;

    public Member getCurrentMember() {
        return memberRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    public GetMemberInfoResponseDto getMemberInfo() {
        return GetMemberInfoResponseDto.from(getCurrentMember());
    }

    @Transactional
    public void editMemberInfo(EditMemberInfoRequestDto editMemberInfoRequestDto) {
        getCurrentMember().editMember(editMemberInfoRequestDto.getName(),
                Address.getAddress(editMemberInfoRequestDto.getCity(),
                        editMemberInfoRequestDto.getStreet(),
                        editMemberInfoRequestDto.getZipcode()
                )
        );  // @Transactional 없이는 getCurrentMember()는 준영속
    }

    public void deleteMember() {
        Member currentMember = getCurrentMember();
        redisService.deleteValues("RT: " + currentMember.getUsername());
        deleteProfileImageIfExists(currentMember);
        memberRepository.delete(currentMember);
    }

    @Transactional
    public String changeProfileImageToNew(MultipartFile profileImage){
        Member currentMember = getCurrentMember();
        deleteProfileImageIfExists(currentMember);
        return currentMember.changeProfileImageUrl(amazonS3Service.uploadFile((profileImage)));
    }

    @Transactional
    public void changeProfileImageToBasic() {
        Member currentMember = getCurrentMember();
        String deleteProfileImageUrl = currentMember.getProfileImageUrl();
        if (deleteProfileImageUrl.equals("basic_profile.png")) {
            throw new AlreadyBasicException();
        }

        // TODO S3에 이미지 저장 후, 확장자 추가 (EX. basic_profile.png)
        currentMember.changeProfileImageUrl("basic_profile.png");
        amazonS3Service.deleteFile(deleteProfileImageUrl);
    }

    public List<GetNotificationInfoResponseDto> getNotificationInfos(List<Notification> notifications) {
        return notifications.stream()
                .map(GetNotificationInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void deleteProfileImageIfExists(Member memberToCheck) {
        if (!memberToCheck.getProfileImageUrl().equals("basic_profile.png")) {
            amazonS3Service.deleteFile(memberToCheck.getProfileImageUrl());
        }
    }
}
