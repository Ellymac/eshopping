package com.myshopping.app;

import javax.ejb.Remote;

/**
 * The API of the order manager entity bean.
 */
@Remote
public interface OrderManager {

    String orderArticle(Customer customer, Article article);

    OrderT findOrder(String id);

    boolean isArticleAvailable(Article a);
}
