package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.config.aws.AmazonS3Service;
import jpabook.jpashop.config.redis.RedisService;
import jpabook.jpashop.domain.member.dto.member.EditMemberInfoRequestDto;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.exception.situation.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final AmazonS3Service amazonS3Service;

    @Transactional(readOnly = true)
    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
    }

    public void editMemberInfo(EditMemberInfoRequestDto editMemberInfoRequestDto) {
        getCurrentMember().editMember(editMemberInfoRequestDto.getName(), getAddress(editMemberInfoRequestDto));
    }

    public void deleteMember() {
        Member currentMember = getCurrentMember();
        redisService.deleteValues("RT: " + currentMember.getUsername());
        deleteProfileImageIfExits(currentMember);
        memberRepository.delete(currentMember);
    }

    public String changeProfileImageToNew(MultipartFile profileImage){
        Member currentMember = getCurrentMember();
        String uploadedProfileImageUrl = amazonS3Service.uploadFile((profileImage));
        deleteProfileImageIfExits(currentMember);
        return currentMember.changeProfileImageUrl(uploadedProfileImageUrl);
    }

    public void changeProfileImageToBasic() {
        Member currentMember = getCurrentMember();
        String deleteProfileImageUrl = currentMember.getProfileImageUrl();
        // TODO S3에 이미지 저장 후, 확장자 추가 (EX. basic.JPEG)
        currentMember.changeProfileImageUrl("basic");
        amazonS3Service.deleteFile(deleteProfileImageUrl);
    }

    private Address getAddress(EditMemberInfoRequestDto editMemberInfoRequestDto) {
        return Address.builder()
                .city(editMemberInfoRequestDto.getCity())
                .street(editMemberInfoRequestDto.getStreet())
                .zipcode(editMemberInfoRequestDto.getZipcode())
                .build();
    }

    private void deleteProfileImageIfExits(Member memberToCheck) {
        // TODO S3에 이미지 저장 후, 확장자 추가 (EX. basic.JPEG)
        if (!memberToCheck.getProfileImageUrl().equals("basic")) {
            amazonS3Service.deleteFile(memberToCheck.getProfileImageUrl());
        }
    }
}
