package com.srishti.webscrapper.repo;

import com.srishti.webscrapper.model.ArticleDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDetailRepo extends CrudRepository<ArticleDetail, Long> {

    List<ArticleDetail> findByTitleContainingAndDescriptionContaining(String title, String description);

    List<ArticleDetail> findByAuthorName(String authorName);
}
