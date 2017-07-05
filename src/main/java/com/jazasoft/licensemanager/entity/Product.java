package com.jazasoft.licensemanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Product extends Auditable<String> {

    @NotNull @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @NotNull @Size(min = 3, max = 5)
    @Column(nullable = false)
    private String productPrefix;

    @OneToMany(mappedBy = "product")
    Set<License> licenses = new HashSet<>();

    public Product() {
    }

    public Product(String name, String description, String productPrefix) {
        this.name = name;
        this.description = description;
        this.productPrefix = productPrefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductPrefix() {
        return productPrefix;
    }

    public void setProductPrefix(String productPrefix) {
        this.productPrefix = productPrefix;
    }

    public Set<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(Set<License> licenses) {
        this.licenses = licenses;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", productPrefix='" + productPrefix + '\'' +
                '}';
    }
}
