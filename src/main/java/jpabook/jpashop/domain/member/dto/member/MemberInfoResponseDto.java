package jpabook.jpashop.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponseDto {

    private String username;
    private String name;
    private Address address;
    private String profileImageUrl;
    private int favorite;

    public static MemberInfoResponseDto from(Member member) {
        return MemberInfoResponseDto.builder()
                .username(member.getUsername())
                .name(member.getName())
                .address(member.getAddress())
                .profileImageUrl(member.getProfileImageUrl())
                .favorite(member.getFavorite())
                .build();
    }
}
