package com.example.untitled27.controller;

import com.example.untitled27.DTO.TodoDto;
import com.example.untitled27.model.Todo;
import com.example.untitled27.model.User;
import com.example.untitled27.repository.TodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    } // 로그인을 했으면 세션을 연결하고 로그아웃하면 세션을 만료한다.

    @GetMapping
    public String list(HttpSession httpSession) {
//        System.out.println(getCurrentUser(httpSession));
        // 로그인하면 인텔리제이에 User 정보 뜨고 로그아웃하고 강제로 접속하면 Null 이 뜬다

        User user = getCurrentUser(httpSession);

        if(user == null) {
            return "redirect:/login"; // 세션이 만료되어 Null 이면 로그인하라고 보낸다
        }
        return "todo-list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession httpSession, Model model) {
        if (getCurrentUser(httpSession) == null) return "redirect:/login";

        model.addAttribute("todoDto", new TodoDto());

        return "todo-form";
    }

    @PostMapping("/add")
    public String add (@Valid @ModelAttribute TodoDto todoDto,
                       BindingResult bindingResult,
                       HttpSession httpSession) {
        if (bindingResult.hasErrors()) return "todo-form";

        User user = getCurrentUser(httpSession);

        Todo todo = Todo.builder()
                        .userId(user.getId())
                        .title(todoDto.getTitle())
                        .completed(todoDto.isCompleted())
                        .build();

        todoRepository.save(todo);

        return "redirect:/todos";
    }
}