package com.nam4o.myweb.domain.article.service;


import com.nam4o.myweb.domain.article.dto.ArticleReqDto;
import com.nam4o.myweb.domain.article.entity.Article;
import com.nam4o.myweb.domain.article.repository.ArticleRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

@Service
@Getter
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;


    // 게시글 생성
    public void createArticle(ArticleReqDto request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .build();
        articleRepository.save(article);
    }

    // 게시글 전체 조회
    public Page<Article> getArticles(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return articleRepository.findAll(pageable);
    }

    // 게시글 상세 조회

    // 게시글 수정

    // 게시글 삭제
}
