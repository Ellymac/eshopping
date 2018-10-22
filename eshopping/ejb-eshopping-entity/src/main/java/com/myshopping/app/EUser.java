package com.myshopping.app;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The EUser entity.
 */
@Entity
public class EUser implements Serializable {
    /**
     * the serial number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * the unique pseudonym of the user.
     */
    @Id
    private String pseudo;
    /**
     * the password of the user.
     */
    private String password;
    /**
     * the first name of the user.
     */
    private String firstName;
    /**
     * the last name of the user.
     */
    private String lastName;
    /**
     * the postal address of the user.
     */
    private String address;
    /**
     * the email address of the user.
     */
    private String email;
    /**
     * the admin flag set to true if user is admin.
     */
    private boolean isAdminFlag;

    @OneToMany(mappedBy = "eUser")
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

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

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

    public boolean isAdminFlag() { return isAdminFlag; }

    public void setisAdminFlag(boolean admin) { isAdminFlag = admin; }

    public List<OrderT> getOrderTS() { return orderTS; }

    public void setOrderTS(List<OrderT> orderTS) { this.orderTS = orderTS; }
}
