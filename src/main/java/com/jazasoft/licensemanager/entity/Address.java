package com.jazasoft.licensemanager.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@Embeddable
public class Address {

    @NotNull @Size(min = 5,max = 100)
    private String street;

    @NotNull @Size(min = 3,max = 50)
    private String city;

    @NotNull @Pattern(regexp="[0-9]{6}")
    private String zipCode;

    @NotNull @Size(min = 3,max = 50)
    private String country;

    public Address() {
    }

    public Address(String street, String city, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
