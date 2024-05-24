package com.nam4o.myweb.domain.article.controller;

import com.nam4o.myweb.common.BaseResponseBody;
import com.nam4o.myweb.domain.article.dto.ArticleListResDto;
import com.nam4o.myweb.domain.article.dto.ArticleReqDto;
import com.nam4o.myweb.domain.article.entity.Article;
import com.nam4o.myweb.domain.article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<? extends BaseResponseBody> postArticle(@RequestBody @Valid ArticleReqDto request){
        articleService.createArticle(request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "게시글 생성 완료"));
    }


    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> getArticleList(@RequestParam(value = "page", defaultValue = "0")int page){
        Page<Article> paging = articleService.getArticles(page - 1);
        ArticleListResDto response = new ArticleListResDto();
        response.setPageSize(articleService.getArticlePageSize());
        response.setArticleList(paging);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, response));
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<? extends BaseResponseBody> getArticleDetail(@PathVariable("article-id") Long articleId) {

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, ""));
    }


}
