package com.kyung.book.springboot.web.dto;

import com.kyung.book.springboot.domain.posts.Posts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 선언된 모든 필드의 get 메소드를 생성해 준다.
@RequiredArgsConstructor    // 선언된 모든 final 필드가 포함된 생성자를 생성해 준다. final이 없는 필드는 생성자에 포함 X
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}
