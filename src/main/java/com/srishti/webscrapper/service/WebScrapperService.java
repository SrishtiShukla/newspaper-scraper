package com.srishti.webscrapper.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.srishti.webscrapper.model.ArticleDetail;
import com.srishti.webscrapper.repo.ArticleDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WebScrapperService {

    Instant start1 = Instant.now();

    @Autowired
    private ArticleDetailRepo articleDetailRepo;

    @Value("${newspaper.archive.link}")
    private String THE_HINDU_ARCHIVE_URL;

    @Autowired
    public WebScrapperService(ArticleDetailRepo articleDetailRepo) {
        this.articleDetailRepo = articleDetailRepo;
    }

    public static List<HtmlAnchor> getLinks(HtmlPage page, String idOrName) {
        ArrayList<HtmlAnchor> l = new ArrayList<>();
        DomNodeList<HtmlElement> anchors = page.getElementsByIdAndOrName(idOrName).get(0).getElementsByTagName("a");
        for (DomElement e : anchors) {
            if (e instanceof HtmlAnchor && !e.getAttribute("href").isEmpty()) {
                HtmlAnchor ahref = (HtmlAnchor) e;
                l.add(ahref);
            }
        }
        return l;
    }

    public String scrapArticle() {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            final HtmlPage archiveHomePage = webClient.getPage(THE_HINDU_ARCHIVE_URL);
            Instant start = Instant.now();

            System.out.println(" ============= Started At  " + start);

            List<HtmlAnchor> anchors = getLinks(archiveHomePage, "archiveWebContainer");
            for (HtmlAnchor anchor : anchors) {
                List<HtmlAnchor> monthlyArchivePage = getLinks(webClient.getPage(anchor.getHrefAttribute()), "archiveDayDatePicker");
                for (HtmlAnchor link : monthlyArchivePage) {
                    HtmlPage dayArchivePage = webClient.getPage(link.getHrefAttribute());

                    List<?> dayArchivePageElements = dayArchivePage.getByXPath("//div[@class='section-container']//ul[@class='archive-list']//li");
                    for (int i = 0, size = dayArchivePageElements == null ? 0 : dayArchivePageElements.size(); i < size; i++) {
                        DomElement element = ((DomElement) dayArchivePageElements.get(i)).getFirstElementChild();
                        if (element instanceof HtmlAnchor) {
                            String href = ((HtmlAnchor) element).getHrefAttribute();
                            System.out.println(" ============= Link  " + href);
                            HtmlPage articlePage = webClient.getPage(href);
                            String articleTitle = element.getTextContent();
                            String author = articlePage.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").size() > 0 ?
                                    articlePage.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").get(0).toString() : "";

                            String description = articlePage.getByXPath("//h2[@class='intro']/text()").size() > 0 ?
                                    articlePage.getByXPath("//h2[@class='intro']/text()").get(0).toString() : "";

                            if (!author.isEmpty()) {
                                ArticleDetail articleDetail = new ArticleDetail();
                                articleDetail.setTitle(articleTitle);
                                articleDetail.setDescription(description);
                                articleDetail.setAuthorName(author);
                                articleDetail.setLink(href);
                                articleDetailRepo.save(articleDetail);
                            }
                        }
                    }
                }
            }

            Instant finish = Instant.now();
            System.out.println(" ============= Finished At  " + finish);

            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println(" ============= Time elapsed  " + timeElapsed);
        } catch (
                IOException e) {

            Instant finish = Instant.now();
            System.out.println(" ============= Finished At  " + finish);
            long timeElapsed = Duration.between(start1, finish).toMillis();
            System.out.println(" ============= Time elapsed  " + timeElapsed);
            e.printStackTrace();
        }

        webClient.close();
        return null;
    }

    public List<ArticleDetail> fetchArticleByAuthor(String author) {
        return articleDetailRepo.findByAuthorName(author);
    }

    public List<ArticleDetail> fetchArticleTitleAndDescription(String articleTitle, String description) {
        return articleDetailRepo.findByTitleContainingAndDescriptionContaining(articleTitle, description);
    }

}
