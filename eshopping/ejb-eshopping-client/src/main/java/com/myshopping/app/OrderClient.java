package com.myshopping.app;

import javax.naming.NamingException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.myshopping.app.UtilsIHM.*;

/**
 * Order client accessing OrderManagers's services
 *
 */
public final class OrderClient {
    /**
     * utility class with no instance.
     */
    private OrderClient() {
    }
    private static ExecutorService executor
            = Executors.newSingleThreadExecutor();

    /**
     *  Réapprovisionnement d'articles lorsqu'ils ne sont plus disponibles.
     * @param input : nb de nouveaux exemplaires
     * @return le nb d'exemplaires en stock
     */
    public static Future<Integer> newDelivery(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input;
        });
    }
    /**
     * the main of the client.
     *
     * @param args
     */
    public static void main( String[] args ) {
    }

    public static void orderInterface() {
        DirectoryManager dm = null;
        OrderManager om = null;
        try {
            dm = BeanManager.getDm();
            om = BeanManager.getOm();
        } catch(NamingException e)
        {
            e.printStackTrace();
            return;
        }
        
        // connexion avant de pouvoir commander
        EUser eUser;
        String pseudo = input("Se connecter en tant que : ");
        eUser = dm.findCustomer(pseudo);
        if(eUser == null) {
            System.out.println("Cet utilisateur n'existe pas.");
            return;
        }

        String pass = input("Mot de passe : ");
        if(!eUser.getPassword().equals(pass)) {
            System.out.println("Mot de passe incorrect");
            return;
        }

        int articleId = inputInt("ID de l'article à commander : ");

        Article article;
        try {
            article = dm.findArticle(articleId);
        } catch (javax.ejb.EJBException e) {
            System.out.println("Cet article n'existe pas.");
            return;
        }

        printArticle(article);
        if (!confirm("\nCommander cet article ?")) {
            return;
        }

        String result = om.orderArticle(eUser, article);
        if (result.equals("NOT OK")) {
            System.out.println("L'article n'est pas disponible.");

            // livraison 10 nouveaux exmplaires
            Future<Integer> future = newDelivery(10);

            while(!future.isDone()) {
                System.out.println("En cours de livraison......");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                dm.updateArticle(article.getId(), article.getDescription(), article.getCategory(), future.get());
                System.out.println("L'article est en stock ! (" + future.get() + " exemplaires)");
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("L'article a été commandé.");
        }

    }

    public static void articleInterface() {
        DirectoryManager dm = null;
        try {
            dm = BeanManager.getDm();
        } catch(NamingException e)
        {
            e.printStackTrace();
            return;
        }

        List<Article> articles = dm.allArticles();
        printArticleList(articles);
    }
}
