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
        System.out.println("\nLà c'est le moment où vous pouvez aller prendre un café...\n");
        try {
            InitialContext ic = new InitialContext();
            dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            om = (OrderManager) ic.lookup("com.myshopping.app.OrderManager");
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        boolean stop = false;
        while (!stop) {
            int choice = menu(
                    "Voir les articles",
                    "Commander",
                    "[ADMIN] Ajouter des utilisateurs et des articles",
                    "Quitter");

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
                case 4:
                    stop = true;
                    break;
            }
        }
    }

    private static void adminInterface() {
        if (!authenticate()) {
            System.out.println("Mauvais mot de passe");
            return;
        }

        boolean stop = false;
        while (!stop) {
            int choice = menu(
                    "Ajouter un utilisateur",
                    "Supprimer un utilisateur",
                    "Ajouter un article",
                    "Supprimer un article",
                    "Menu principal");

            switch (choice) {
                case 1:
                    addUserInterface();
                    break;
                case 2:
                    delUserInterface();
                    break;
                case 3:
                    addArticleInterface();
                    break;
                case 4:
                    delArticleInterface();
                    break;
                case 5:
                    stop = true;
                    break;
            }
        }
    }

    private static void delArticleInterface() {
        int articleId = inputInt("L'ID de l'article à supprimer : ");
        try {
            dm.deleteArticle(dm.findArticle(articleId));
            System.out.println("L'article a été supprimé.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("Cet article n'existe pas.");
        }
    }

    private static void addArticleInterface() {
        System.out.println("\nNouvel article");
        int id = inputInt(" > ID : ");
        String description = input(" > Description : ");
        String category = input(" > Catégorie : ");
        try {
            dm.insertArticle(id, description, category);
            System.out.println("L'article a été ajouté.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("L'article n'a pas pu être ajouté (essayez avec une autre ID).");
        }
    }

    private static void delUserInterface() {
        String pseudo = input("Le pseudo de l'utilisateur à supprimer : ");
        try {
            dm.deleteCustomer(dm.findCustomer(pseudo));
            System.out.println("L'utilisateur a été supprimé.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("Cet utilisateur n'existe pas.");
        }
    }

    private static void addUserInterface() {
        System.out.println("\nNouvel utilisateur");
        String pseudo = input(" > Pseudo : ");
        String firstName = input (" > Prénom : ");
        String lastName = input (" > Nom : ");
        String address = input(" > Adresse : ");
        String email = input (" > Courriel : ");
        try {
            dm.insertCustomer(pseudo, firstName, lastName, address, email);
            System.out.println("L'utilisateur a été ajouté.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("L'utilisateur n'a pas pu être ajouté (le pseudo est peut-être déjà pris).");
        }
    }

    private static void orderInterface() {
        Customer customer;
        try {
            String pseudo = input("Se connecter en tant que : ");
            customer = dm.findCustomer(pseudo);
        } catch (javax.ejb.EJBException e) {
            System.out.println("Cet utilisateur n'existe pas.");
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
        String idColumnName = "ID";
        String descriptionColumnName = "DESCRIPTION";
        String categoryColumnName = "CATEGORY";
        String availableColumnName = "AVAILABLE";

        System.out.println();
        if (articles.size() == 0) {
            System.out.println("Il n'y a pas d'article dans la base de données.");
            System.out.println("Vous pouvez en ajouter avec l'interface administrateur (choix 3 dans le menu principal).");
            return;
        }

        // Calcule la taille des colonnes
        int idColumnSize = max(5, idColumnName.length());
        int descriptionColumnSize = descriptionColumnName.length();
        int categoryColumnSize = categoryColumnName.length();
        int availableColumnSize = max(5, availableColumnName.length());
        for (Article article : articles) {
            descriptionColumnSize = max(descriptionColumnSize, article.getDescription().length());
            categoryColumnSize = max(categoryColumnSize, article.getCategory().length());
        }

        // Affiche les en-têtes
        System.out.println(" " + pad(idColumnName, idColumnSize) + " | " + pad(descriptionColumnName, descriptionColumnSize) + " | " + pad(categoryColumnName, categoryColumnSize) + " | " + pad(availableColumnName, availableColumnSize));
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
            int choice = inputInt(" > ");
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
        if (input.equals(PASSWORD)) {
            isAuthenticated = true;
            return true;
        } else {
            return false;
        }
    }

    private static boolean confirm(String prompt) {
        String in = input(prompt + " [O/n] ");
        return in.equals("O") || in.equals("o") || in.equals("");
    }

    private static int inputInt(String prompt) {
        int res = 0;
        while (true) {
            String inputId = input(prompt);

            try {
                res = Integer.valueOf(inputId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Veullez entrer un nombre.");
            }
        }

        return res;
    }

    private static String input(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        System.out.flush();
        return in.nextLine().trim();
    }
}
