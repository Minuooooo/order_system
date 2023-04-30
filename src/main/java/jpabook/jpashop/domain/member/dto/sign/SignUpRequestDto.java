package jpabook.jpashop.domain.member.dto.sign;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

//    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
//    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰 번호는 하이픈(-)을 포함해야 합니다.") // 휴대폰 번호 형식
//    @Schema(description = "휴대폰 번호", defaultValue = "010-1234-5678")
//    private String phone;

//    @NotBlank(message = "생년월일을 입력해주세요.")
//    @Pattern(regexp = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", message = "생년월일은 하이픈(-)을 포함해야 합니다.") // 생년월일 형식
//    @Schema(description = "생년월일", defaultValue = "2000-01-01")
//    private String birth;
}
