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
        String pseudo = input("Se connecter en tant que : ");
        EUser eUser = dm.findCustomer(pseudo);

        int articleId = 0;
        while (true) {
            String inputId = input("ID de l'article à commander : ");

            try {
                articleId = Integer.valueOf(inputId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre.");
            }
        }

        Article article = dm.findArticle(articleId);
        printArticle(article);
        if (!confirm("Commander cet article ?")) {
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
