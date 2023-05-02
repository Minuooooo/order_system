package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.config.aws.AmazonS3Service;
import jpabook.jpashop.config.redis.RedisService;
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

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
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
        currentMember.changeProfileImageUrl("basic");
        amazonS3Service.deleteFile(deleteProfileImageUrl);
    }

    private void deleteProfileImageIfExits(Member memberToCheck) {
        if (!memberToCheck.getProfileImageUrl().equals("basic")) {
            amazonS3Service.deleteFile(memberToCheck.getProfileImageUrl());
        }
    }
}
