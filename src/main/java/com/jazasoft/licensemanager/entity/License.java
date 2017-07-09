package com.jazasoft.licensemanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jazasoft.licensemanager.validation.FixedEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mdzahidraza on 02/07/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class License extends Auditable<String>{

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @Transient
    private Long productId;

    @NotNull
    @Transient
    private Long clientId;

    @Column(nullable = false, unique = true)
    private String productCode;

    @Column(nullable = false)
    private String productKey;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date purchasedOn;

    @Temporal(TemporalType.DATE)
    private Date activatedOn;

    @Column(nullable = false)
    private boolean activated;

    private String macId;

    @NotNull @FixedEnum(enumClass = LicenseType.class)
    @Column(nullable = false)
    private String licenseType;

    @NotNull @FixedEnum(enumClass = LicenseFlavour.class)
    @Column(nullable = false)
    private String licenseFlavour;

    @NotNull
    @Column(nullable = false)
    private Integer validity;   // -1: perpetual, n: Annual|Trial   [in number of days]

    @NotNull
    private Long noOfUsers;  // Perpetual: Not applicable, Trial|Annual: No of active Users

    public License() {
        super();
    }

    public License(Long productId, Long clientId, String licenseType, String licenseFlavour, Integer validity, Long noOfUsers) {
        this.productId = productId;
        this.clientId = clientId;
        this.licenseType = licenseType;
        this.licenseFlavour = licenseFlavour;
        this.validity = validity;
        this.noOfUsers = noOfUsers;
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

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getLicenseFlavour() {
        return licenseFlavour;
    }

    public void setLicenseFlavour(String licenseFlavour) {
        this.licenseFlavour = licenseFlavour;
    }
}
