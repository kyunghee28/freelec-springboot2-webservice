package com.kyung.book.springboot.service.posts;

import com.kyung.book.springboot.domain.posts.Posts;
import com.kyung.book.springboot.domain.posts.PostsRepository;
import com.kyung.book.springboot.web.dto.PostsResponseDto;
import com.kyung.book.springboot.web.dto.PostsSaveRequestDto;
import com.kyung.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // JPA의 영속성 컨텍스트 때문에 update 쿼리를 날리는 부분이 없다.
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        return new PostsResponseDto(entity);
    }
}
