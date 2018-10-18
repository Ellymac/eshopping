package com.myshopping.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The order of the customers.
 */
@Entity
@Table(name = "ORDER_TABLE")
public class Order {
    /**
     * the identifier.
     */
    private int id;
    /**
     * the shipping address.
     */
    private String address;
    /**
     * the corresponding customer.
     */
    private Customer customer;

    /**
     * gets the identifier.
     *
     * @return the identifier.
     */
    @Id
    @Column(name = "ORDER_ID")
    public int getId() {
        return id;
    }

    /**
     * sets the identifier.
     *
     * @param id
     *            the new identifier.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * gets the shipping address.
     *
     * @return the shipping address.
     */
    @Column(name = "SHIPPING_ADDRESS")
    public String getAddress() {
        return address;
    }

    /**
     * sets the shipping address.
     *
     * @param address
     *            the new address.
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * gets the customer.
     *
     * @return the customer.
     */
    @ManyToOne()
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    /**
     * sets the customer.
     *
     * @param customer
     *            the customer.
     */
    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }
}
