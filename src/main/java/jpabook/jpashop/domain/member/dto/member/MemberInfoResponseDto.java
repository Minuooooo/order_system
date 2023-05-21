package jpabook.jpashop.domain.member.dto.member;

import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoResponseDto {

    private String username;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String profileImageUrl;

    public static MemberInfoResponseDto from(Member member) {
        return MemberInfoResponseDto.builder()
                .username(member.getUsername())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
