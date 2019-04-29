package com.srishti.webscrapper.model;

import javax.persistence.*;

@Entity
@Table(name = "article_detail", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"link", "author_name"})
})
public class ArticleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "link")
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}