package com.kyung.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    /*
        MyBatis 에서 Dao라도 불리는 DB Layer 접근자
        JPA 에선 Repository 라고 부르며 인터페이스로 생성
        JpaRepository<Entity 클래스, PK 타입> 를 상속하면 기본적인 CRUD 메소드가 자동으로 생성

        @Repository 를 추가할 필요가 없다.
        Entity 클래스와 기본 Entity Repository 는 함께 위치해야 한다.
        Entity 클래스는 기본 Repository 없니는 제대로 역활을 할 수 없다.
    */

}