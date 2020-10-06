package com.sk.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing 삭제
@SpringBootApplication //스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정, 특히 이 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 최상단에 위치해야함
public class Application { //프로젝트의 메인 클래스
    public static void main(String[] args){
        SpringApplication.run(Application.class, args); //내장 WAS(Web Application Server) 실행
    }
}
