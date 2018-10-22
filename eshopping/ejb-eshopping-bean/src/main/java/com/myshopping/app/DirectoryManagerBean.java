package com.myshopping.app;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
        EUser c = new EUser();
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
    public EUser findCustomer(String pseudo) {
        Query q = em.createQuery("select c from EUser c where c.pseudo = :pseudo");
        q.setParameter("pseudo", pseudo);
        return (EUser) q.getSingleResult();
    }

    @Override
    public String deleteCustomer(EUser cust) {
        EUser c = em.merge(cust);
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
        Query q = em.createQuery("select a from Article a where a.id = :id");
        q.setParameter("id", id);
        return (Article) q.getSingleResult();
    }

    //@Override
    public List<Article> allArticles() {
        return em.createQuery("FROM Article", Article.class).getResultList();
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

    @Override
    public boolean isArticleAvailable(Article a) {
        return a.getNbAvailable() > 0;
    }
}
