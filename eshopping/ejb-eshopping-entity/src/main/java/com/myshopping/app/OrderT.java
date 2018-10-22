package com.myshopping.app;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class OrderT implements Serializable {
    @Id
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EUSER_ID")
    private EUser eUser;

    private Date buyedAt;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public EUser geteUser() {
        return eUser;
    }

    public void seteUser(EUser eUser) {
        this.eUser = eUser;
    }

    public Date getBuyedAt() {
        return buyedAt;
    }

    public void setBuyedAt(Date buyedAt) {
        this.buyedAt = buyedAt;
    }
}
