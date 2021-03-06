package com.jazasoft.licensemanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jazasoft.licensemanager.validation.Fixed;
import com.jazasoft.licensemanager.validation.FlavourFixedValue;
import org.springframework.data.domain.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(indexes = @Index(name = "PRODUCT_IDX", columnList = "name"))
public class Product extends Auditable<String> {

    @NotNull @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @NotNull @Size(min = 3, max = 5)
    @Column(nullable = false)
    private String productPrefix;

    @NotNull @Fixed(fixClass = FlavourFixedValue.class)
    private String flavours;

    @OneToMany(mappedBy = "product")
    Set<License> licenses = new HashSet<>();

    public Product() {
    }

    public Product(String name, String description, String productPrefix, String flavours) {
        this.name = name;
        this.description = description;
        this.productPrefix = productPrefix;
        this.flavours = flavours;
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

//    public List<String> getFlavours() {
//        List<String> flavourList = new ArrayList<>();
//        if (this.flavours == null) return flavourList;
//        String[] items = this.flavours.split(",");
//        for (String item: items) {
//            flavourList.add(item);
//        }
//        return flavourList;
//    }
//
//    public void setFlavours(List<String> flavours) {
//        StringBuilder builder = new StringBuilder();
//        flavours.forEach(flavour -> builder.append(flavour).append(","));
//        if (builder.length() > 0) {
//            builder.setLength(builder.length()-1);
//        }
//        this.flavours = builder.toString();
//    }


    public String getFlavours() {
        return flavours;
    }

    public void setFlavours(String flavours) {
        this.flavours = flavours;
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

