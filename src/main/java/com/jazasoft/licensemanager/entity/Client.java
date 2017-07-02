package com.jazasoft.licensemanager.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@Entity
public class Client extends Auditable<String> {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<License> licenses = new HashSet<>();

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<License> getLicenses() {
        return licenses;
    }

    public void addLicense(License license) {
        this.licenses.add(license);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
