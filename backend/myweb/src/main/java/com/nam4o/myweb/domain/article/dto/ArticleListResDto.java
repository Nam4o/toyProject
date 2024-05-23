package com.nam4o.myweb.domain.article.dto;

import com.nam4o.myweb.domain.article.entity.Article;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class ArticleListResDto {

    private Long pageSize;

    private Page<Article> articleList;
}
