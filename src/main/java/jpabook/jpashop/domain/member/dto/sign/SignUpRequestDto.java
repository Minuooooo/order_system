package jpabook.jpashop.domain.member.dto.sign;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Authority;
import jpabook.jpashop.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일은 @를 포함해야 합니다.") // 이메일 형식
    @Schema(description = "이메일", defaultValue = "test@gmail.com")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.") // 비밀번호 형식
    @Schema(description = "비밀번호", defaultValue = "test1234!")
    private String password;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 2, message = "사용자 이름이 너무 짧습니다.")
    @Schema(description = "이름", defaultValue = "홍길동")
    private String name;

    @NotBlank(message = "시,도,군을 입력해주세요.")
    @Schema(description = "시,도,군", defaultValue = "경기도")
    private String city;

    @NotBlank(message = "도로명을 입력해주세요.")
    @Schema(description = "도로명", defaultValue = "고양이 네로")
    private String street;

    @NotBlank(message = "우편번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{5}$", message = "우편번호는 5자리이어야 합니다.") // 우편번호 형식
    @Schema(description = "우편번호", defaultValue = "11111")
    private String zipcode;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(this.getUsername())
                .password(validateExistsByPassword(passwordEncoder))  // 일반, 소셜 회원가입을 비밀번호 유무로 구분
                .name(this.getName())
                .address(Address.getAddress(this.city, this.street, this.zipcode))
                .profileImageUrl("basic_profile.png")  // TODO S3에 이미지 저장 후, 확장자 추가 (EX. basic_profile.png)
                .favorite(0)
                .authority(Authority.ROLE_USER)
                .build();
    }

    private String validateExistsByPassword(PasswordEncoder passwordEncoder) {  // 비밀번호가 있다면 일반, 없다면 소셜 회원가입
        if (!StringUtils.hasText(this.getPassword())) {
            return passwordEncoder.encode(UUID.randomUUID().toString());
        } else {
            return passwordEncoder.encode(this.getPassword());
        }
    }
}
