package com.srishti.webscrapper;

import com.srishti.webscrapper.model.ArticleDetail;
import com.srishti.webscrapper.repo.ArticleDetailRepo;
import com.srishti.webscrapper.service.WebScrapperService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


public class WebScrapperServiceTest {


    @Mock
    private ArticleDetailRepo mockArticleDetailRepo;

    private WebScrapperService webScrapperServiceUnderTest;
    private ArticleDetail articleDetail;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webScrapperServiceUnderTest = new WebScrapperService(mockArticleDetailRepo);
        articleDetail = new ArticleDetail();
        articleDetail.setAuthorName("Srishti");
        articleDetail.setTitle("Srishti's Article");
        articleDetail.setDescription("Testing");
        articleDetail.setLink("Testing");

        Mockito.when(mockArticleDetailRepo.save(any()))
                .thenReturn(articleDetail);
        Mockito.doReturn(mockArticleDetailRepo.findByAuthorName(anyString()));
    }

    @Test
    public void testFindByAuthorName() {
        final String authorName = "Srishti";
        final ArticleDetail result = webScrapperServiceUnderTest.fetchArticleByAuthor(authorName).get(0);
        Assert.assertEquals(authorName, result.getAuthorName());
    }

}
