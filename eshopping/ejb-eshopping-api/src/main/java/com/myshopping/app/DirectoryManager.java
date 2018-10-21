package com.myshopping.app;

import javax.ejb.Remote;

/**
 * The API of the directory manager entity bean.
 */
@Remote
public interface DirectoryManager {
    /**
     * Insert Customer.
     *
     * @return the string "OK" if there is no problem.
     */
    String insertCustomer(String pseudo, String fname, String lname, String addr, String email);
    /**
     * Find Customer.
     *
     * @return the customer or null if not found.
     */
    Customer findCustomer(String pseudo);
    /**
     * Delete Customer.
     *
     * @return the string "OK" if there is no problem.
     */
    String deleteCustomer(Customer cust);
    /**
     * Update Customer.
     *
     * @return the string "OK" if there is no problem.
     */
    String updateCustomer(String pseudo, String fname, String lname, String addr, String email);

    /**
     * Insert Article.
     *
     * @return the string "OK" if there is no problem.
     */
    String insertArticle(int id, String desc, String cat);
    /**
     * Find Article.
     *
     * @return the article or null if not found.
     */
    Article findArticle(int id);
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
    String updateArticle(int id, String desc, String cat);
    /**
     * Check Article availability
     *
     * @return true if available, false if not available.
     */
    boolean isArticleAvailable(Article a);
}
