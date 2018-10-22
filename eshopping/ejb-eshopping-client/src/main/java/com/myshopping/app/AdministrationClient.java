package com.myshopping.app;


import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    /**
     * utility class with no instance.
     */
    private AdministrationClient() {
        // Cimer le java on veut juste faire des fonctions mais on est obligé d'utiliser des classes.
    }

    /**
     * the main of the client.
     *
     * @param args
     */
    public static void main( String[] args )
    {
        DirectoryManager dm;
        Customer c;

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


    }

    private static void orderInterface() {
    }

    private static void articleInterface() {
        try {
            InitialContext ic = new InitialContext();
            DirectoryManager dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            List<Article> articles = dm.allArticles();
            printArticleList(articles);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private static void printArticleList(List<Article> articles) {
        int numColumns = 4;

        // Calcule la taille des colonnes
        int idColumnSize = max(5, "ID".length());
        int descriptionColumnSize = 0;
        int categoryColumnSize = 0;
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
        Scanner in = new Scanner(System.in);
        System.out.println("Mot de passe : ");
        String input = in.next().trim();
        return input.equals(PASSWORD);
    }
}
