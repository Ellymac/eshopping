package com.myshopping.app;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The directory manager bean.
 */
@Stateless
public class DirectoryManagerBean implements DirectoryManager {

    /**
     * the reference to the entity manager, which persistence context is "pu1".
     */
    @PersistenceContext(unitName = "pu1")
    private EntityManager em;


    @Override
    public String insertCustomer(String pseudo, String fname, String lname, String addr, String email) {
        // create the customer
        Customer c = new Customer();
        c.setPseudo(pseudo);
        c.setFirstName(fname);
        c.setLastName(lname);
        c.setAddress(addr);
        c.setEmail(email);
        // persist the customer
        em.persist(c);
        return "OK";
    }

    @Override
    public Customer findCustomer(String pseudo) {
        Query q = em.createQuery("select cust from Customer c where cust.pseudo = :pseudo");
        q.setParameter("pseudo", pseudo);
        return (Customer) q.getSingleResult();
    }

    @Override
    public String deleteCustomer(Customer cust) {
        Customer c = em.merge(cust);
        // delete all records
        em.remove(c);
        return "OK";
    }

    @Override
    public String updateCustomer(String pseudo, String fname, String lname, String addr, String email) {
        return null;
    }

    @Override
    public String insertArticle(int id, String desc, String cat) {
        // create the article
        Article a = new Article();
        a.setId(id);
        a.setDescription(desc);
        a.setCategory(cat);
        // persist article
        em.persist(a);
        return "OK";
    }

    @Override
    public Article findArticle(int id) {
        Query q = em.createQuery("select art from Article a where art.id = :id");
        q.setParameter("id", id);
        return (Article) q.getSingleResult();
    }

    @Override
    public String deleteArticle(Article art) {
        Article a = em.merge(art);
        // delete all records
        em.remove(a);
        return "OK";
    }

    @Override
    public String updateArticle(int id, String desc, String cat) {
        return null;
    }
}
