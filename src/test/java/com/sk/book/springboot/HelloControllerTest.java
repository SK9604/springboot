package com.sk.book.springboot;

import com.sk.book.springboot.config.auth.SecurityConfig;
import com.sk.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다 (SpringRunner라는 스프링 실행자 사용)
                             //즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다
@WebMvcTest(controllers = HelloController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
}) //Web(Spring MVC)에 집중할 수 있는 어노테이션
                                                 //@Controller, @ControllerAdvice 등 사용
                                                 //@Service, @Component, @Repository 등 사용 불가
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 빈(Bean)을 주입받음
    private MockMvc mvc; //웹API를 테스트할 때 사용. 스프링 MVC 테스트의 시작점
                         //이 클래스를 통해 HTTP GET, POST등에 대한 API 테스트를 할 수 있다
    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET 요청을 함, 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다
                .andExpect(status().isOk()) //mvc.perform의 결과를 검증, HTTP Header의 Status를 검증
                .andExpect(content().string(hello)); //mvc.perform의 결과를 검증, 응답 본문의 내용을 검증
    }
    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
            get("/hello/dto")
                    .param("name",name) //param: API테스트할 때 사용될 요청 파라미터를 설정. 단, 값은 String만 허용됨
                    .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //jsonpath: JSON 응답값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명을 명시함.
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
