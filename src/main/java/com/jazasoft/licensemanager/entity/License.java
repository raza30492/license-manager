package com.jazasoft.licensemanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@Entity
public class License extends Auditable<String>{

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private String productKey;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date purchasedOn;

    @Column(nullable = false)
    private boolean activated;

    private String macId;

    private String licenseType;

    @Column(nullable = false)
    private Integer validity;   // -1: perpetual, n: Annual|Trial   [in number of days]

    private Long noOfUsers;  // Perpetual: Not applicable, Trial|Annual: No of active Users

    public License() {
        super();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Date getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(Date purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Long getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(Long noOfUsers) {
        this.noOfUsers = noOfUsers;
    }
}
