package jpabook.jpashop.domain.member.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Schema(description = "이메일", defaultValue = "test@gmail.com")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "비밀번호", defaultValue = "test1234!")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
