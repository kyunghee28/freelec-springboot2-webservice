package com.kyung.book.springboot.service.posts;

import com.kyung.book.springboot.domain.posts.Posts;
import com.kyung.book.springboot.domain.posts.PostsRepository;
import com.kyung.book.springboot.web.dto.PostsListResponseDto;
import com.kyung.book.springboot.web.dto.PostsResponseDto;
import com.kyung.book.springboot.web.dto.PostsSaveRequestDto;
import com.kyung.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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

    // 게시글 출력
    @Transactional(readOnly = true)  // 조회기능만 남겨두어 속도 개선
    public List<PostsListResponseDto> findAllDesc() {

        // postsRepository 결과로 넘어온 Posts 의 stream 을 map을 통해 PostsListResponseDto 변환 -> List 로 변환
        return postsRepository.findAllDesc().stream()
                                .map(PostsListResponseDto::new)   // .map(posts -> new PostsListResponseDto(posts)) 와 같다.
                                .collect(Collectors.toList());
    }
}