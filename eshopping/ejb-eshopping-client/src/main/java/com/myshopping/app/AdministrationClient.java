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
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        adminInterface();
    }

    public static void adminInterface() {
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

    public static boolean authenticate() {
        if (isAuthenticated) {
            return true;
        }
        String input = input("Mot de passe : ");
        return true;
    }
}
