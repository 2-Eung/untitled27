package com.example.untitled27.controller;

import com.example.untitled27.DTO.LoginDto;
import com.example.untitled27.model.User;
import com.example.untitled27.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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
public class LoginController {
    private  final UserRepository userRepository;

    @GetMapping({"/", "/login"})
    public String showLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "login";
    } // DTO 활용
    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                          BindingResult bindingResult,
                          HttpSession httpSession,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            User user = userRepository.findByUsername(loginDto.getUsername());
            // 유저가 입력한 패스워드 와 DTO 에 있는 패스워드가 일치하는가

            if(!user.getPassword().equals(loginDto.getPassword())) {
                model.addAttribute("error", "비밀번호가 올바르지 않습니다");

                return "login";
            }

            httpSession.setAttribute("user", user);

            return "redirect:/todos";
        } catch(Exception e) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");

            return "login";
        }
    }
//html 은 기본적으로 stateless
//스테이트리스 stateless
//    : 상태없음 서버가 들어오는것들의 상태를 기억하지 않음
//    들어온것과 연결관계가 없음 그래서 매번 확인을 받아야함 (비밀번호로) 그래서 불편
//해결방안으로 세션 이있음
//    : 서버 들어오면 서버 메모리에 들어오는것과 연결된 토큰을 만들고 이 토큰으로 확인을 받는것이다.

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션을 만료시킨다

        return "redirect:/login"; // 세션이 없으니 되돌아간다.
    }
}
