package com.example.untitled27.controller;

import com.example.untitled27.DTO.SignupDto;
import com.example.untitled27.model.User;
import com.example.untitled27.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserRepository userRepository;

//    @GetMapping("/signup")
//    public String showSignup(Model model) {
//        model.addAttribute("signupDto");
//
//        return "signup";
//    } // 예전엔 이렇게 썻지만 DTO 를 쓸거여서 DTO 형식으로 바꿀 것이다

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    } // DTO 활용
                            // 서버에서 받아올때 검증해야하므로 controller 에서 적는다.
    @PostMapping("/signup") // @Valid : 검증절차를 수행해라 라는 어노테이션 (검증방법은 DTO에 명시)
    public String doSignup(@Valid @ModelAttribute SignupDto signupDto, // 검사하기
                           BindingResult bindingResult, // 검사결과 저장하기 (요번에 새로 추가한 SpringBoot 기능 validation 에 포함)
                           Model model) {
        if (userRepository.findByUsername(signupDto.getUsername()) != null) { // 중복 가입 여부 체크
            model.addAttribute("error", "이미 사용 중인 아이디입니다");

            return "signup";             // 중복이면 다시 만들어라
        }
        User user = User.builder()
                .username(signupDto.getUsername())
                .password(signupDto.getPassword())
                .build();
        userRepository.save(user); // 저장

        return "redirect:/login?registered";
    }


}
