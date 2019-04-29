package com.srishti.webscrapper.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WebScrapperService {

    public String scrapArticles(String baseUrl) {

        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect(baseUrl).get();

            // With the document fetched, we use JSoup's title() method to fetch the title
            System.out.printf("Title: %s\n", doc.title());

            /*How it works. The ul li i a is a css selector.
            Which would mean take every a element that is located inside i
            that is wrapped in li tags which is wrapped in ul tags. (Horrible explanation)

            You would get the same result from doc.select("a") as well.
            But being specific is better since you're parsing this data from some website because
            links can be in different places with different id/class or whatever and you are looking
            for these specific ones.
            */
            Elements links = doc.getElementsByClass("archiveMonthList").select("ul li a");
            /*for (Element link : links) {
//                System.out.println("link : " + link.attr("href"));
//                System.out.println("text : " + link.text());

                String detailPageUrl = "";

                // go to a detail page
                String companyPageUrl = "";
                detailPageUrl = link.attr("abs:href");
                Document doc2 = Jsoup.connect(detailPageUrl).get();
                Elements companyPageLink = doc2.getElementsByClass("ui-state-default");
                for (Element link2 : companyPageLink) {

                    companyPageUrl = link2.attr("href");
//                System.out.println("link : " + companyPageUrl);

                    Document doc3 = Jsoup.connect(companyPageUrl).get();
                    Elements nextCompanyPageLink = doc3.getElementsByClass("archive-list").select("ul li a");
                    for (Element link3 : nextCompanyPageLink) {
                        String nextCompanyPageUrl = link3.attr("href");
                        System.out.println("link : " + nextCompanyPageUrl);

                        Document doc4 = Jsoup.connect(nextCompanyPageUrl).get();
                        String author = doc4.getElementsByClass("auth-nm lnk").text();
                        System.out.println("Author " + author);
                    }
                }


            }*/

//            return (doc.title());

            //get first link
            String link = Jsoup.connect(baseUrl).get().select("div.archiveContainer archiveMonthList a").get(0).attr("href");
            //an int just to count up links
//            int i = 1;
//            System.out.println("pagination-link_"+ i + "\t" + link);
            //parse next page using link
            //check if the div on next page has more than one link in it
//            while(Jsoup.connect(link).get().select("div.pagination a").size() >1){
//                link = Jsoup.connect(link).get().select("div.pagination a").get(1).attr("href");
//                System.out.println("pagination-link_"+ (++i) +"\t" + link);
//            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String scrapArticle(String baseUrl) {

        try (final WebClient webClient = new WebClient()) {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect(baseUrl).get();

            // With the document fetched, we use JSoup's title() method to fetch the title
            System.out.printf("Title: %s\n", doc.title());

            Elements links = doc.getElementsByClass("archiveMonthList").select("ul li a");

            int i;
            Element tempLink;
            for (i = 0; i < links.size(); i++) {
                tempLink = links.get(i);
                System.out.println("Link : " + tempLink.attr("href"));
                System.out.println("text : " + tempLink.text());

                Document doc2 = Jsoup.connect(tempLink.attr("href")).get();
                Elements calanderLinks = doc2.getElementsByClass("ui-state-default");

                int j;
                Element tempJLink;
                for (j = 0; j < calanderLinks.size(); j++) {
                    tempJLink = calanderLinks.get(i);
                    System.out.println("Link : " + tempJLink.attr("href"));
                    System.out.println("text : " + tempJLink.text());
                }
            }
//                final HtmlPage page = webClient.getPage(baseUrl);
//                final HtmlDivision div = page.getHtmlElementById("archiveWebContainer");
//            page.getHtmlElementById("archiveWebContainer").getChildNodes();
//                final HtmlAnchor anchor = page.getAnchorByName("archiveMonthList");


            return (doc.title());


        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String scrapArticleUsingHTMLUnit(String baseUrl) {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            final HtmlPage archiveHomePage = webClient.getPage(baseUrl);
            final HtmlPage archiveHomePage2 = webClient.getPage(baseUrl);
            Instant start = Instant.now();

            System.out.println(" ============= Started At  " + start);

            List<HtmlAnchor> anchors = getLinks(archiveHomePage, "archiveWebContainer");
            for(HtmlAnchor anchor: anchors) {
                List<HtmlAnchor> monthlyArchivePage = getLinks(webClient.getPage(anchor.getHrefAttribute()), "archiveDayDatePicker");
                for(HtmlAnchor link: monthlyArchivePage){
//                    List<HtmlAnchor> dayArchivePage = getLinks(webClient.getPage(link.getHrefAttribute()), "section-container");
                    HtmlPage dayArchivePage = webClient.getPage(link.getHrefAttribute());
                    List<DomAttr> articlePage = dayArchivePage.getByXPath("//div[@class='section-container']//a/@href");
                    for (DomAttr articleLink: articlePage) {
                        HtmlPage articlePage3 = webClient.getPage(articleLink.getValue());
                        if(!articlePage3.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").isEmpty()) {
                            String articleTitle = articlePage3.getByXPath("//h1[@class='title']/text()").toString();
                            String author = articlePage3.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").toString();
                            System.out.println(" ============= ArticleTitle  " + articleTitle + " ============== Author " + author);
                        }
                    }
                    }
            }

//            final HtmlDivision div = archiveHome.getHtmlElementById("archiveWebContainer");
//            final HtmlDivision div2 = archiveHome.getHtmlElementById("archiveTodayContainer");
//
//            Iterable<DomNode> domNodes = archiveHome.getHtmlElementById("archiveWebContainer")
//                    .getChildren();
//
//            List<DomNode> aa = archiveHome.getByXPath("//div[@class='archiveMonthList']//a");


//            HtmlAnchor htmlAnchor = page2.getAnchorByHref("/romarin/detail.do?ID=0");


//            List<HtmlAnchor> archiveMonthLinks = archiveHomePage.getByXPath("//ul[@class='archiveMonthList']//a");
//            List<DomAttr> archiveMonthLinks = archiveHomePage.getByXPath("//div[@id='archiveWebContainer']//a/@href");
//            for (DomAttr link : archiveMonthLinks) {
//                System.out.println("Month Number : " + link.getTextContent());
//                System.out.println("Month Link : " + link.getValue());
//                HtmlPage monthlyArchivePage = webClient.getPage(link.getValue());
//                List<DomAttr> archiveDayLinks =
//                        monthlyArchivePage.getByXPath("//table[@class='archiveTable']//tbody//tr//td//a/@href");
//                for (DomAttr archiveDayLink : archiveDayLinks) {
//                    HtmlPage dayArchivePage = webClient.getPage(link.getValue());
//                    List<DomAttr> articleLinks =
//                            dayArchivePage.getByXPath("//div[@class='tpaper-container']//a/@href");
//                    for (DomAttr articleLink : articleLinks) {
//                        if (!articleLink.getValue().isEmpty()) {
//                            HtmlPage articlePage = webClient.getPage(articleLink.getValue());
//                            List<DomAttr> articleLinks2 = articlePage.getByXPath("//div[@class='section-container']//a/@href");
//                            for (DomAttr articleLink3 : articleLinks2) {
//                                HtmlPage articlePage3 = webClient.getPage(articleLink3.getValue());
//                                if(!articlePage3.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").isEmpty()) {
//                                    String articleTitle = articlePage3.getByXPath("//h1[@class='title']/text()").toString();
//                                    String author = articlePage3.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").toString();
//                                    System.out.println(" ============= ArticleTitle  " + articleTitle + " ============== Author " + author);
//                                }
//                            }
//                        }
//                    }
//                }
//            }

            Instant finish = Instant.now();
            System.out.println(" ============= Finished At  " + finish);

            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println(" ============= Time elapsed  " + timeElapsed);

//            final HtmlSubmitInput button = form.getInputByName("submit");
//            final HtmlPage page2 = button.click();
//            System.out.println(page2.asText());
//            final HtmlForm form2 = page2.getFormByName("SearchForm");
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        webClient.close();
        return null;
    }

    public static List<HtmlAnchor> getLinks(HtmlPage page, String idOrName) {
        ArrayList<HtmlAnchor> l = new ArrayList<>();
        DomNodeList<HtmlElement> anchors = page.getElementsByIdAndOrName(idOrName).get(0).getElementsByTagName("a");
        for (DomElement e : anchors) {
            if (e instanceof HtmlAnchor && !e.getAttribute("href").isEmpty()) {
                HtmlAnchor ahref = (HtmlAnchor) e;
                l.add(ahref);
                System.out.println("============ " + ahref.getHrefAttribute());
            }
        }
        return l;
    }


//    public String scrapArticleUsingHTMLUnitAndDS(String baseUrl) throws IOException {
//        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        try {
//
//            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setJavaScriptEnabled(false);
//
//            final HtmlPage archiveHomePage = webClient.getPage(baseUrl);
//            Instant start = Instant.now();
//
//            System.out.println(" ============= Started At  " + start);
//
//            ArrayList<String>  archiveMonthLinksArray = new ArrayList<>();
//            List<DomAttr> archiveMonthLinks = archiveHomePage.getByXPath("//div[@id='archiveWebContainer']//a/@href");
//            for (DomAttr link : archiveMonthLinks) {
//                System.out.println("Month Number : " + link.getTextContent());
//                System.out.println("Month Link : " + link.getValue());
//                archiveMonthLinksArray.add(link.getValue());
//
//            }
//
//            for(String archiveLink : archiveMonthLinksArray) {
//                HtmlPage monthlyArchivePage = webClient.getPage(archiveLink);
//                List<DomAttr> archiveDayLinks =
//                        monthlyArchivePage.getByXPath("//table[@class='archiveTable']//tbody//tr//td//a/@href").;
//                ArrayList<String> archiveDayLinksArray =
//            }
//                for (DomAttr archiveDayLink : archiveDayLinks) {
//                    System.out.println("Archive Link : " + archiveDayLink.getValue());
//                    HtmlPage dayArchivePage = webClient.getPage(link.getValue());
//                    List<DomAttr> articleLinks =
////                            dayArchivePage.getByXPath("//div[@class='section-container']//ul//li//a]");
//                            dayArchivePage.getByXPath("//div[@class='tpaper-container']//a/@href");
//                    for (DomAttr articleLink : articleLinks) {
//                        System.out.println("Article Link : " + articleLink.getValue());
//                        if (!articleLink.getValue().isEmpty()) {
//                            HtmlPage articlePage = webClient.getPage(articleLink.getValue());
//                            List<DomAttr> articleLinks2 = articlePage.getByXPath("//div[@class='section-container']//a/@href");
//                            for (DomAttr articleLink3 : articleLinks2) {
////                            if(articlePage3.getByXPath("//div[@class='title']").size()>0) {
//                                System.out.println("Article2 Link : " + articleLink3.getValue());
//                                HtmlPage articlePage3 = webClient.getPage(articleLink3.getValue());
//                                if(!articlePage3.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").isEmpty()) {
//                                    String articleTitle = articlePage3.getByXPath("//h1[@class='title']/text()").toString();
//                                    String author = articlePage3.getByXPath("//div[@class='author-container hidden-xs']//a[@class='auth-nm lnk']/text()").toString();
//                                    System.out.println(" ============= ArticleTitle  " + articleTitle + " ============== Author " + author);
//                                }
//                            }
//                        }
//                    }
//                }
//
//            Instant finish = Instant.now();
//            System.out.println(" ============= Finished At  " + finish);
//
//            long timeElapsed = Duration.between(start, finish).toMillis();
//            System.out.println(" ============= Time elapsed  " + timeElapsed);
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//
//        webClient.close();
//        return null;
//    }


    protected List getAllElementsOfGivenClass(HtmlElement root, List list, Class matchClass) {
        if (null == root) {
            return list;
        }
        if (null == list) {
            list = new ArrayList();
        }
        Iterable<DomElement> iterable = root.getChildElements();
        Iterator<DomElement> iter = iterable.iterator();
        while (iter.hasNext()) {
            getAllElementsOfGivenClass((HtmlElement) iter.next(), list, matchClass);
        }
        if (matchClass.isInstance(root)) {
            if (!list.contains(root)) {
                list.add(root);
            }
        }
        return list;
    }


}
