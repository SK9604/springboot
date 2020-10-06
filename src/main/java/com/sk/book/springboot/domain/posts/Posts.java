package com.sk.book.springboot.domain.posts;

import com.sk.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //롬복:클래스 내 모든 필드의 Getter 메소드 자동생성
@NoArgsConstructor //롬복:기본 생성자 자동 추가 (pubilc Posts(){}와 같은 효과)
@Entity //JPA:테이블과 링크될 클래스임을 나타냄, 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다(ex SalesManager.java->sales_manager table)

public class Posts extends BaseTimeEntity {
    @Id //JPA:해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA:PK 생성 규칙을 나타냄
    private Long id;

    @Column(length = 500, nullable = false) //JPA:테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다, 기본값 외에 추가로 변경이 필요한 옵션이 있는경우 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //롬복:해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

/*
//잘못 사용된 예
public class Order{
    public void setStatus(boolean status){
        this.status = status;
    }
}
public void 주문서비스의_최소이벤트(){
    order.setStatus(false);
}

//올바른 사용 예
public class Order{
    public void cancelOrder(){
        this.status = false;
    }
}
public void 주문서비스의_취소이벤트 (){
    order.cancelOrder();
}
 */