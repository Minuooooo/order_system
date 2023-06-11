package jpabook.jpashop.domain.chatroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class GetEnteredMemberInfoResponseDto {

    private Long id;
    private String name;
    private String profileImageUrl;

    public static GetEnteredMemberInfoResponseDto from(Member EnteredMember) {
        return GetEnteredMemberInfoResponseDto.builder()
                .id(EnteredMember.getId())
                .name(EnteredMember.getName())
                .profileImageUrl(EnteredMember.getProfileImageUrl())
                .build();
    }
}
