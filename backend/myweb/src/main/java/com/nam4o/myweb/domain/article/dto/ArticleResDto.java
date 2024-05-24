package com.nam4o.myweb.domain.article.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleResDto {

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;

    private String writter;

}
