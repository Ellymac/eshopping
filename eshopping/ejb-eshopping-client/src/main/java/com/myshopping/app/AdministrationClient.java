package com.myshopping.app;


import javax.naming.InitialContext;

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
        DirectoryManager dm;
        Customer c;
        try {
            InitialContext ic = new InitialContext();
            dm = (DirectoryManager) ic.lookup("com.myshopping.app.DirectoryManager");
            System.out.println("Inserting Customer... " + dm.insertCustomer("pseudo","john","doe","addr 88 Paris","john.doe@unknown.tv"));
            /* Test query and navigation
            System.out.println("Verifying that all are inserted... " + sb.verifyInsert());
            // Get a detached instance
            c = sb.findCustomer("Bat Man");
            // Remove entity
            System.out.println("Removing entity... " + sb.testDelete(c));
            // Query the results
            System.out.println("Verifying that all are removed... " + sb.verifyDelete());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( "I am Groot" );
    }
}
