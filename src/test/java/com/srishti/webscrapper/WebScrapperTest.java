package com.srishti.webscrapper;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

public class WebScrapperTest {

    private static final String THE_HINDU_BASE_URL = "https://www.thehindu.com/archive/";

    @Test
    public void homePage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(THE_HINDU_BASE_URL);
            Assert.assertEquals("Archive News - The Hindu", page.getTitleText());
//
//            final String pageAsXml = page.asXml();
//            Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
//
//            final String pageAsText = page.asText();
//            Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }
    }
}
