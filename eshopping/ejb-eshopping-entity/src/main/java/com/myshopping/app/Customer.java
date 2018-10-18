package com.myshopping.app;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The customer entity.
 */
@Entity
public class Customer implements Serializable {
    /**
     * the serial number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * the unique pseudonym of the customer.
     */
    private String pseudo;
    /**
     * the first name of the customer.
     */
    private String firstName;
    /**
     * the last name of the customer.
     */
    private String lastName;
    /**
     * the postal address of the customer.
     */
    private String address;
    /**
     * the email address of the customer.
     */
    private String email;
    /**
     * the collection of orders.
     */
    private Collection<Order> orders = new ArrayList<Order>();


    /**
     * gets the unique pseudonym.
     *
     * @return the pseudonym.
     */
    @Id
    public String getPseudo() {
        return pseudo;
    }

    /**
     * sets the pseudonym.
     *
     * @param pseudo
     *            the new pseudonym.
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the collection of orders.
     *
     * @return the collection.
     */
    @OneToMany(cascade = ALL, mappedBy = "customer")
    public Collection<Order> getOrders() {
        return orders;
    }

    /**
     * sets the collection of orders.
     *
     * @param newValue
     *            the new collection.
     */
    public void setOrders(final Collection<Order> newValue) {
        this.orders = newValue;
    }

}
