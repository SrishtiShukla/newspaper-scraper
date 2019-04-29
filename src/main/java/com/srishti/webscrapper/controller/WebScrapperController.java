package com.srishti.webscrapper.controller;

import com.srishti.webscrapper.model.ArticleDetail;
import com.srishti.webscrapper.service.WebScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scrapper")
public class WebScrapperController {

    @Autowired
    WebScrapperService webScrapperService;

    @Value("${spring.application.name}")
    String appName;

    @Autowired
    public WebScrapperController(ApplicationContext applicationContext, WebScrapperService webScrapperService) {
        this.webScrapperService = webScrapperService;
    }

    @GetMapping(name = "Scrap articles", value = "/")
    public String scrapArticles() {
        return webScrapperService.scrapArticle();
    }

    @GetMapping(name = "Search articles of an author Or search an author", value = {"/search/{authorName}/article", "/search/{authorName}"})
    public List<ArticleDetail> searchAuthorArticles(@PathVariable String authorName) {
        return webScrapperService.fetchArticleByAuthor(authorName);
    }

    @GetMapping(name = "Search articles based on title and description", value = "/search")
    public List<ArticleDetail> searchArticleTitleAndDescription(@RequestParam(name = "articleTitle") String articleTitle,
                                                                @RequestParam(name = "description") String description) {
        return webScrapperService.fetchArticleTitleAndDescription(articleTitle, description);
    }

}
