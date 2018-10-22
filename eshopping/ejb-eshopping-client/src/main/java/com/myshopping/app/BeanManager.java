package com.myshopping.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Bean manager to initialize DirectoryManager and OrderManager instances
 */
public class BeanManager {
    private static DirectoryManager dm = null;
    private static OrderManager om = null;
    private static InitialContext ic = null;

    private BeanManager() {
    }

    public static DirectoryManager getDm() throws NamingException {
        initDm();
        return dm;
    }

    private static void initDm() throws NamingException {
        if (dm == null) {
            try {
                ic = new InitialContext();
                dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            } catch (NamingException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    // la même pour getOm()

    public static OrderManager getOm() throws NamingException {
        initOm();
        return om;
    }

    private static void initOm() throws NamingException {
        if (om == null) {
            try {
                ic = new InitialContext();
                om = (OrderManager) ic.lookup("com.myshopping.app.OrderManager");
            } catch (NamingException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
