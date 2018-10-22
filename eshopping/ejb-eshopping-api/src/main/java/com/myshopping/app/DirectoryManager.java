package com.myshopping.app;

import javax.ejb.Remote;
import java.util.List;

/**
 * The API of the directory manager entity bean.
 */
@Remote
public interface DirectoryManager {
    /**
     * Insert EUser.
     *
     * @return the string "OK" if there is no problem.
     */
    String insertCustomer(String pseudo, String pass, String fname, String lname, String addr, String email, boolean admin);

    /**
     * Find EUser.
     *
     * @return the customer or null if not found.
     */
    EUser findCustomer(String pseudo);

    /**
     * Delete EUser.
     *
     * @return the string "OK" if there is no problem.
     */
    String deleteCustomer(EUser cust);

    /**
     * Update EUser.
     *
     * @return the string "OK" if there is no problem.
     */
    String updateCustomer(String pseudo, String pass, String fname, String lname, String addr, String email, boolean admin);

    /**
     * Insert Article.
     *
     * @return the string "OK" if there is no problem.
     */
    String insertArticle(int id, String desc, String cat, int nbAvailable);

    /**
     * Find Article.
     *
     * @return the article or null if not found.
     */
    Article findArticle(int id);

    List<Article> allArticles();

    /**
     * Delete Article.
     *
     * @return the string "OK" if there is no problem.
     */
    String deleteArticle(Article art);

    /**
     * Update Article.
     *
     * @return the string "OK" if there is no problem.
     */
    String updateArticle(int id, String desc, String cat, int nb);

    /**
     * Check Article availability
     *
     * @return true if available, false if not available.
     */
    boolean isArticleAvailable(Article a);
}
