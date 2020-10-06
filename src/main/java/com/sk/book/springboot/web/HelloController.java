package com.sk.book.springboot.web;

import com.sk.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다
public class HelloController {

    @GetMapping("/hello") //HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 준다
    public String Hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, //외부에서 API로 넘긴 파라미터를 가져오는 어노테이션.
                                     @RequestParam("amount") int amount ){ //외부에서 name이란 이름으로 넘긴 파라미터를 메소드 파라미터 name에 저장하게 됨
        return new HelloResponseDto(name, amount);
    }
}
