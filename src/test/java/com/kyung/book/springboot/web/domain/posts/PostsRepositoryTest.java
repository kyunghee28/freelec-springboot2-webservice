package com.kyung.book.springboot.web.domain.posts;

import com.kyung.book.springboot.domain.posts.Posts;
import com.kyung.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After  // junit 에서 단위 테스트가 끝날 때마다 수행되는 메소드 지정
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        /// given
        String title = "테스트 제목";
        String content = "테스트 본문";

        postsRepository.save(   // 테이블 posts에 insert/update 쿼리를 실행 (id 값이 있으면 update, 없으면 insert)
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("test@test.com")
                        .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터 조회

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}
