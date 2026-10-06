package com.example.customermanager;

/**
 * A simple Customer model.
 * Groups a customer's details together so a TableView row
 * can read them through the getters below.
 */
public class Customer {

    private final String name;
    private final String province;

    public Customer(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }
}
