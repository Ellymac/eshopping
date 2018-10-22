package com.myshopping.app;

import java.io.Serializable;
import java.util.List;
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
    @Id
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

    @OneToMany(mappedBy = "customer")
    private List<OrderT> orderTS;

    /**
     * gets the unique pseudonym.
     *
     * @return the pseudonym.
     */
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
}
