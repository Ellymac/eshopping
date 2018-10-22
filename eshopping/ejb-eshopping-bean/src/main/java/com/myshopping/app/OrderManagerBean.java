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
    public String orderArticle(Customer customer, Article article) {
        int nbAvailable = article.getNbAvailable();

        if (nbAvailable == 0) {
            return "NOT OK";
        }

        Order order = new Order();
        order.setArticle(article);
        order.setCustomer(customer);
        order.setBuyedAt(new Date());
        em.persist(order);

        article.setNbAvailable(nbAvailable - 1);
        em.persist(article);

        return "OK";
    }

    @Override
    public Order findOrder(String id) {
        return em.find(Order.class, id);
    }

    @Override
    public boolean isArticleAvailable(Article a) {
        return a.getNbAvailable() > 0;
    }

}
