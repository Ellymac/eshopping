package com.myshopping.app;


import javax.naming.InitialContext;
import javax.naming.NamingException;

import static com.myshopping.app.UtilsIHM.*;

/**
 * Administration client accessing DirectyManagers's services
 *
 */
public final class AdministrationClient
{
    private static DirectoryManager dm = null;

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
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        adminInterface();
    }

    public static void adminInterface() {
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
}
