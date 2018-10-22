package com.myshopping.app;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.max;

/**
 * Administration client accessing DirectyManagers's services
 *
 */
public final class AdministrationClient
{
    private static final String PASSWORD = "salut";

    private static DirectoryManager dm = null;
    private static OrderManager om = null;

    private static boolean isAuthenticated = false;

    /**
     * utility class with no instance.
     */
    private AdministrationClient() {
    }

    /**
     * the main of the client.
     *
     * @param args
     */
    public static void main( String[] args )
    {
        try {
            InitialContext ic = new InitialContext();
            dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            om = (OrderManager) ic.lookup("com.myshopping.app.OrderManager");
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        int choice = menu("Voir les articles", "Commander", "[ADMIN] Ajouter des utilisateurs et des articles");

        switch (choice) {
            case 1:
                articleInterface();
                break;
            case 2:
                orderInterface();
                break;
            case 3:
                adminInterface();
                break;
        }
    }

    private static void adminInterface() {
        if (!authenticate()) {
            System.out.println("Mauvais mot de passe");
            return;
        }

        int choice = menu(
                "Ajouter un utilisateur",
                "Supprimer un utilisateur",
                "Ajouter un article",
                "Supprimer un article");
    }

    private static void orderInterface() {
        String pseudo = input("Se connecter en tant que : ");
        Customer customer = dm.findCustomer(pseudo);

        int articleId = 0;
        while (true) {
            String inputId = input("ID de l'article à commander : ");

            try {
                articleId = Integer.valueOf(inputId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Veullez entrer un nombre.");
            }
        }

        Article article = dm.findArticle(articleId);
        printArticle(article);
        if (!confirm("Commander cet article ?")) {
            return;
        }

        String result = om.orderArticle(customer, article);
        if (result.equals("NOT OK")) {
            System.out.println("L'article n'est pas disponible.");
        } else {
            System.out.println("L'article a été commandé.");
        }
    }

    private static void articleInterface() {
        List<Article> articles = dm.allArticles();
        printArticleList(articles);
    }

    private static void printArticle(Article article) {
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        printArticleList(articleList);
    }

    private static void printArticleList(List<Article> articles) {
        int numColumns = 4;

        System.out.println();
        if (articles.size() == 0) {
            System.out.println("Il n'y a pas d'article dans la base de données.");
            System.out.println("Vous pouvez en ajouter avec l'interface administrateur (choix 3 dans le menu principal).");
            return;
        }

        // Calcule la taille des colonnes
        int idColumnSize = max(5, "ID".length());
        int descriptionColumnSize = "DESCRIPTION".length();
        int categoryColumnSize = "CATEGORY".length();
        int availableColumnSize = max(5, "AVAILABLE".length());
        for (Article article : articles) {
            descriptionColumnSize = max(descriptionColumnSize, article.getDescription().length());
            categoryColumnSize = max(categoryColumnSize, article.getCategory().length());
        }

        // Affiche les en-têtes
        System.out.println(" " + pad("ID", idColumnSize) + " | " + pad("DESCRIPTION", descriptionColumnSize) + " | " + pad("CATEGORY", categoryColumnSize) + " | " + pad(" AVAILABLE", availableColumnSize));
        System.out.println(new String(new char[idColumnSize + descriptionColumnSize + categoryColumnSize + availableColumnSize + numColumns * 3 - 1]).replace("\0", "-"));

        // Affiche le contenu
        for (Article article : articles) {
            System.out.println(" " +
                    pad(String.valueOf(article.getId()), idColumnSize) + " | " +
                    pad(article.getDescription(), descriptionColumnSize) + " | " +
                    pad(article.getCategory(), categoryColumnSize) + " | " +
                    pad(String.valueOf(article.getNbAvailable()), availableColumnSize));
        }
    }

    private static String pad(String s, int size) {
        if (size == 0) return "";
        return new String(new char[size - s.length()]).replace("\0", " ") + s;
    }

    private static int menu(String... choices) {
        Scanner in = new Scanner(System.in);

        System.out.println();
        for (int i = 0; i < choices.length; i++) {
            System.out.println(" " + String.valueOf(i + 1) + ") " + choices[i]);
        }

        while (true) {
            System.out.print("> ");
            System.out.flush();

            int choice = in.nextInt();
            if (0 < choice && choice <= choices.length) {
                return choice;
            } else {
                System.out.println("Veuillez entrer un nombre entre 1 et " + String.valueOf(choices.length));
            }
        }
    }

    private static boolean authenticate() {
        if (isAuthenticated) {
            return true;
        }
        String input = input("Mot de passe : ");
        return input.equals(PASSWORD);
    }

    private static boolean confirm(String prompt) {
        String in = input(prompt + " [O/n] ");
        return in.equals("O") || in.equals("o") || in.equals("");
    }

    private static String input(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        System.out.flush();
        return in.next().trim();
    }
}
