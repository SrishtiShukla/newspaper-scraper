package com.srishti.webscrapper.controller;

import com.srishti.webscrapper.service.WebScrapperService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scrapper")
public class WebScrapperController {
    private static final Logger logger = LogManager.getLogger(WebScrapperController.class);

    private static final String SUCCESS_MSG = "Retrieval of data successful ";

    private static final String ERROR_MSG = "Fatal Error occurred while retrieving ";

    private static final String NO_DATA_MSG = "No Data could be retrieved ";

    private static final String THE_HINDU_BASE_URL = "https://www.thehindu.com/archive/";

    @Autowired
    WebScrapperService webScrapperService;

    @Autowired
    public WebScrapperController(ApplicationContext applicationContext, WebScrapperService webScrapperService) {
        this.webScrapperService = webScrapperService;
    }

    @GetMapping(name = "Scrap articles", value = "/scrap")
    public String scrapArticles() {
//       return webScrapperService.scrapArticles(THE_HINDU_BASE_URL);
//       return webScrapperService.scrapArticle(THE_HINDU_BASE_URL);
       return webScrapperService.scrapArticleUsingHTMLUnit(THE_HINDU_BASE_URL);

    }

}
