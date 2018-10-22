package com.myshopping.app;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Order implements Serializable {
    private String id;

    @OneToOne
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getBuyedAt() {
        return buyedAt;
    }

    public void setBuyedAt(Date buyedAt) {
        this.buyedAt = buyedAt;
    }
}
