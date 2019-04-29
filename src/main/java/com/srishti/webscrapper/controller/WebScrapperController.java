package com.srishti.webscrapper.controller;

import com.srishti.webscrapper.model.ArticleDetail;
import com.srishti.webscrapper.service.WebScrapperService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scrapper")
public class WebScrapperController {
    private static final Logger logger = LogManager.getLogger(WebScrapperController.class);

    private static final String SUCCESS_MSG = "Retrieval of data successful ";

    private static final String ERROR_MSG = "Fatal Error occurred while retrieving ";

    private static final String NO_DATA_MSG = "No Data could be retrieved ";

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
