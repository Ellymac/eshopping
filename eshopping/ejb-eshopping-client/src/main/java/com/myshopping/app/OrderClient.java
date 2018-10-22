package com.myshopping.app;

import javax.naming.InitialContext;
import java.util.List;

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

    private static DirectoryManager dm = null;
    private static OrderManager om = null;

    /**
     * the main of the client.
     *
     * @param args
     */
    public static void main( String[] args ) {
        System.out.println("I am Order Client");
    }

    public static void orderInterface() {

        // connexion avant de pouvoir commander
        EUser eUser;
        try {
            String pseudo = input("Se connecter en tant que : ");
            eUser = dm.findCustomer(pseudo);
        } catch (javax.ejb.EJBException e) {
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
        } else {
            System.out.println("L'article a été commandé.");
        }

    }

    public static void articleInterface() {
        List<Article> articles = dm.allArticles();
        printArticleList(articles);
    }
}
