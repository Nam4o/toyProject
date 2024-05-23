package com.nam4o.myweb.domain.article.service;


import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.article.dto.ArticleReqDto;
import com.nam4o.myweb.domain.article.entity.Article;
import com.nam4o.myweb.domain.article.repository.ArticleRepository;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import com.nam4o.myweb.util.SecurityUtility;
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
    private final MemberRepository memberRepository;


    // 게시글 생성
    public void createArticle(ArticleReqDto request) {
        Member member = memberRepository.findByEmail(SecurityUtility.getCurrentUserEmail())
                .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));

        Article article = Article.builder()
                .writer(member.getName())
                .member(member)
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

    public Long getArticlePageSize() {
        return articleRepository.count();
    }

    // 게시글 상세 조회

    // 게시글 수정

    // 게시글 삭제
}
