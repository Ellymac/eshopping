package com.myshopping.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.max;

/**
 * Utils containing print/prompt functions used multiple times in clients
 */
public class UtilsIHM {

    public static void printArticle(Article article) {
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        printArticleList(articleList);
    }

    public static void printArticleList(List<Article> articles) {
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

    public static String pad(String s, int size) {
        if (size == 0) return "";
        return new String(new char[size - s.length()]).replace("\0", " ") + s;
    }

    public static int menu(String... choices) {
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

    public static boolean confirm(String prompt) {
        String in = input(prompt + " [O/n] ");
        return in.equals("O") || in.equals("o") || in.equals("");
    }

    public static String input(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        System.out.flush();
        return in.next().trim();
    }

    public static int inputInt(String prompt) {
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
}
