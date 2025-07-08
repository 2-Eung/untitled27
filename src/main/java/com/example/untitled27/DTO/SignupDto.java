package com.example.untitled27.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    @NotBlank(message = "아이디를 입력하세요") // 공백 이면 안됨 (공백인지 확인)
    @Size(min = 3, max = 10, message = "아이디는 3 ~ 10자 여야 합니다.") // 최고 3자 최대 10 자
    // 요번에 새로 추가한 SpringBoot 기능 validation
    private String username;   // 주고 받을 필드 생성
    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 6, max = 20, message = "패스워드는 6 ~ 20자 여야 합니다.")
    private String password;
} // DTO 는 서버와 주고 받는 역할을 하기때문에 게터 와 세터 가 핵심이다.

//public @interface Size {
//    String message() default "";
//    int min() default 0;
//    int max() default Integer.MAX_VALUE;
//} // @Size 는 이런식으로 되어있다.
