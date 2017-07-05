package com.jazasoft.licensemanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Email;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Client extends Auditable<String> {

    @NotNull @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull @Valid
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<License> licenses = new HashSet<>();

    public Client() {
    }

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
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
