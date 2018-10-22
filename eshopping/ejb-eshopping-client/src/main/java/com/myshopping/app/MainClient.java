package com.myshopping.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import static com.myshopping.app.AdministrationClient.*;
import static com.myshopping.app.OrderClient.*;
import static com.myshopping.app.UtilsIHM.*;

/**
 * Maint client with interactive console app.
 */
public class MainClient {
    /**
     * utility class with no instance.
     */
    private MainClient() {
    }

    private static DirectoryManager dm = null;
    private static OrderManager om = null;

    private static boolean isAuthenticated = false;

    /**
     * the main of the client.
     *
     * @param args
     */
    public static void main( String[] args ) {
        try {
            InitialContext ic = new InitialContext();
            dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            om = (OrderManager) ic.lookup("com.myshopping.app.OrderManager");
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        boolean run = true;
        while (run) {
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
                    run = false;
                    break;
            }
        }
    }
}
