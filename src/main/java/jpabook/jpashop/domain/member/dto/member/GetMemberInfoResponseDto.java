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
public class GetMemberInfoResponseDto {

    private String username;
    private String name;
    private Address address;
    private String profileImageUrl;
    private int favorite;

    public static GetMemberInfoResponseDto from(Member member) {
        return GetMemberInfoResponseDto.builder()
                .username(member.getUsername())
                .name(member.getName())
                .address(member.getAddress())
                .profileImageUrl(member.getProfileImageUrl())
                .favorite(member.getFavorite())
                .build();
    }
}
