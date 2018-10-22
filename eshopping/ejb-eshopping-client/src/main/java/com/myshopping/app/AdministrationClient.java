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
    }

    public static void adminInterface() {
        DirectoryManager dm = null;
        try {
            dm = BeanManager.getDm();
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
        else if(!eUser.isAdminFlag())
        {
            System.out.print("Vous n'avez pas les droits suffisants.");
            return;
        }

        boolean stop = false;
        while (!stop) {
            int choice = menu(
                    "Ajouter un utilisateur",
                    "Supprimer un utilisateur",
                    "Ajouter un article",
                    "Supprimer un article");
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
        DirectoryManager dm = null;
        try {
            dm = BeanManager.getDm();
        } catch(NamingException e)
        {
            e.printStackTrace();
            return;
        }

        int articleId = inputInt("L'ID de l'article à supprimer : ");
        try {
            dm.deleteArticle(dm.findArticle(articleId));
            System.out.println("L'article a été supprimé.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("Cet article n'existe pas.");
        }
    }

    private static void addArticleInterface() {
        DirectoryManager dm = null;
        try {
            dm = BeanManager.getDm();
        } catch(NamingException e)
        {
            e.printStackTrace();
            return;
        }

        System.out.println("\nNouvel article");
        int id = inputInt(" > ID : ");
        String description = input(" > Description : ");
        String category = input(" > Catégorie : ");
        int nb = inputInt("> Nombre d'articles disponibles : ");
        try {
            dm.insertArticle(id, description, category, nb);
            System.out.println("L'article a été ajouté.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("L'article n'a pas pu être ajouté (essayez avec une autre ID).");
        }
    }

    private static void delUserInterface() {
        DirectoryManager dm = null;
        try {
            dm = BeanManager.getDm();
        } catch(NamingException e)
        {
            e.printStackTrace();
            return;
        }

        String pseudo = input("Le pseudo de l'utilisateur à supprimer : ");
        EUser userToDelete = dm.findCustomer(pseudo);
        if(userToDelete != null) {
            dm.deleteCustomer(userToDelete);
            System.out.println("L'utilisateur a été supprimé.");
        }
        else
        {
            System.out.println("L'utilisateur n'existe pas.");
            return;
        }
    }

    private static void addUserInterface() {
        DirectoryManager dm = null;
        try {
            dm = BeanManager.getDm();
        } catch(NamingException e)
        {
            e.printStackTrace();
            return;
        }
        
        System.out.println("\nNouvel utilisateur");
        String pseudo = input(" > Pseudo : ");
        String mdp = input(" > Mot de passe : ");
        String firstName = input (" > Prénom : ");
        String lastName = input (" > Nom : ");
        String address = input(" > Adresse : ");
        String email = input (" > Courriel : ");
        boolean admin = input("> Admin (y/n) : ").equals("y") ? true : false;
        try {
            dm.insertCustomer(pseudo, mdp, firstName, lastName, address, email, admin);
            System.out.println("L'utilisateur a été ajouté.");
        } catch (javax.ejb.EJBException e) {
            System.out.println("L'utilisateur n'a pas pu être ajouté (le pseudo est peut-être déjà pris).");
        }
    }
}
