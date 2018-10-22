package com.myshopping.app;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * The order manager bean.
 */
@Stateless
public class OrderManagerBean implements OrderManager {

    @PersistenceContext(unitName = "pu1")
    private EntityManager em;

    @Override
    public String orderArticle(EUser eUser, Article article) {
        int nbAvailable = article.getNbAvailable();

        if (nbAvailable == 0) {
            return "NOT OK";
        }

        OrderT orderT = new OrderT();
        orderT.setArticle(article);
        orderT.seteUser(eUser);
        orderT.setBuyedAt(new Date());
        em.persist(orderT);

        article.setNbAvailable(nbAvailable - 1);
        em.persist(article);

        return "OK";
    }

    @Override
    public OrderT findOrder(String id) {
        return em.find(OrderT.class, id);
    }

    @Override
    public boolean isArticleAvailable(Article a) {
        return a.getNbAvailable() > 0;
    }

}
