package com.myshopping.app;

import javax.ejb.EJBException;
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
    public String insertCustomer(String pseudo, String pass, String fname, String lname, String addr, String email, boolean admin) {
        // create the customer
        EUser c = new EUser();
        c.setPseudo(pseudo);
        c.setPassword(pass);
        c.setFirstName(fname);
        c.setLastName(lname);
        c.setAddress(addr);
        c.setEmail(email);
        c.setisAdminFlag(admin);
        // persist the customer
        em.persist(c);
        return "OK";
    }

    @Override
    public EUser findCustomer(String pseudo) {
        EUser u = null;
        try {
            Query q = em.createQuery("select c from EUser c where c.pseudo = :pseudo");
            q.setParameter("pseudo", pseudo);
            u = (EUser) q.getSingleResult();
        }  catch(Exception e)
        {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public String deleteCustomer(EUser cust) {
        EUser c = em.merge(cust);
        // delete all records
        em.remove(c);
        return "OK";
    }

    @Override
    public String updateCustomer(String pseudo, String pass, String fname, String lname, String addr, String email, boolean isAdmin) {
        EUser u = new EUser();
        u.setPseudo(pseudo);
        u.setPassword(pass);
        u.setFirstName(fname);
        u.setLastName(lname);
        u.setAddress(addr);
        u.setEmail(email);
        u.setisAdminFlag(isAdmin);
        em.merge(u);
        return "OK";
    }

    @Override
    public String insertArticle(int id, String desc, String cat, int nbAvailable) {
        // create the article
        Article a = new Article();
        a.setId(id);
        a.setDescription(desc);
        a.setCategory(cat);
        a.setNbAvailable(nbAvailable);
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
        return em.createQuery("FROM Article a", Article.class).getResultList();
    }

    @Override
    public String deleteArticle(Article art) {
        Article a = em.merge(art);
        // delete all records
        em.remove(a);
        return "OK";
    }

    @Override
    public String updateArticle(int id, String desc, String cat, int nb) {
        Article a = new Article();
        a.setDescription(desc);
        a.setId(id);
        a.setCategory(cat);
        a.setNbAvailable(nb);
        em.merge(a);
        return "OK";
    }

    @Override
    public boolean isArticleAvailable(Article a) {
        return a.getNbAvailable() > 0;
    }
}
