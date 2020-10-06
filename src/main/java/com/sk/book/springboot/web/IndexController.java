package com.sk.book.springboot.web;

import com.sk.book.springboot.config.auth.LoginUser;
import com.sk.book.springboot.config.auth.dto.SessionUser;
import com.sk.book.springboot.service.posts.PostsService;
import com.sk.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { //기존에 (User) httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선되었다, 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었다
        model.addAttribute("posts", postsService.findAllDesc());

        if(user!=null){ // 세션에 저장된 값이 있을 때만 model에 userName으로 등록합니다
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
