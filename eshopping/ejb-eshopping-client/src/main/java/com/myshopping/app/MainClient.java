package com.myshopping.app;

import javax.enterprise.inject.spi.Bean;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static com.myshopping.app.AdministrationClient.*;
import static com.myshopping.app.OrderClient.*;
import static com.myshopping.app.UtilsIHM.*;

/**
 * Main client with interactive console app.
 */
public class MainClient {
    /**
     * utility class with no instance.
     */
    private MainClient() {
    }

    /**
     * the main of the client.
     *
     * @param args
     */
    public static void main( String[] args ) {
        int choice = menu("Voir les articles", "Commander", "[ADMIN] Ajouter des utilisateurs et des articles");

        // create default admin account to access admin part of the app
        try {
            if(BeanManager.getDm().findCustomer("admin") == null)
                BeanManager.getDm().insertCustomer("admin", "admin", "John", "Doe", "nowhere New York", "john.doe@unknown.do", true);
        } catch (NamingException e) {
            e.printStackTrace();
        }

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
}
