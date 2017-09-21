package com.jazasoft.licensemanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull @Size(min = 3, max = 50)
    private String jobTitle;

    @JsonIgnore
    //@MapsId
    @OneToOne(mappedBy = "company")
    //@JoinColumn(name = "company_id")
    private User user;

    @NotNull
    @Valid
    @Embedded
    private Address address;

    public Company() {
    }

    public Company(String name, String jobTitle) {
        this.name = name;
        this.jobTitle = jobTitle;
    }

    public Company(String name) {
        this.name = name;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", address=" + address +
                '}';
    }
}
