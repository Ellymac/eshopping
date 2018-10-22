package com.myshopping.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Scenario1 extends Scenario {
    public static void main(String[] args) {
        DirectoryManager dm;
        OrderManager om;

        try {
            InitialContext ic = new InitialContext();
            dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            om = (OrderManager) ic.lookup("com.myshopping.app.OrderManager");
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        thenDo("Création de deux utilisateurs 'Jean' et 'Alice'", () -> {
            dm.insertCustomer("Jean", "Jean", "naeJ", "1 rue un 01001 Unville", "jean@jean.org");
            dm.insertCustomer("Alice", "\\", "'", "1 rue un 01001 --", "jean@jean.org");
            return null;
        });

        thenDo("Création de deux articles 'jouet' et 'ps4 evolution'", () -> {
            dm.insertArticle(1, "jouet", "jeux");
            dm.insertArticle(2, "ps4 evolution", "jeux");
            return null;
        });

        thenDo("Jean commande un jouet", () -> {
            EUser jean = dm.findCustomer("Jean");
            Article jouet = dm.findArticle(1);
            return om.orderArticle(jean, jouet);
        });

        thenDo("Suppression de la ps4 evolution", () -> {
            Article article = dm.findArticle(2);
            dm.deleteArticle(article);
            return null;
        });
    }
}
