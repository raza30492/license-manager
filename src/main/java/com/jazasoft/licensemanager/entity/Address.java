package com.jazasoft.licensemanager.entity;

import org.hibernate.validator.constraints.NotEmpty;

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
    private String line1;

    private String line2;

    @NotNull @Size(min = 3,max = 50)
    private String city;

    @NotNull @Size(min = 3,max = 50)
    private String state;

    @NotNull @Size(min = 3,max = 50)
    private String country;

    @NotNull @Pattern(regexp="[0-9]{6}")
    private String zipCode;

    public Address() {
    }

    public Address(String line1, String line2, String city, String state, String country, String zipCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address(String line1, String city, String zipCode, String country) {
        this.line1 = line1;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "Address{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
