package com.myshopping.app;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The article entity
**/
@Entity
@Table(name = "ARTICLE_TABLE")
public class Article {
    /**
     * the unique identifier.
     */
    private int id;
    /**
     * the description of the article

     */
    private String description;
    /**
     * the category of the article
     */
    private String category;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}