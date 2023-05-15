package jpabook.jpashop.domain.member.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsernameResponseDto {
    private String email;

    public static UsernameResponseDto toDto(String email) {
        return UsernameResponseDto.builder()
                .email(email)
                .build();
    }
}
