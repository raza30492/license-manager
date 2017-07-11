package com.jazasoft.licensemanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jazasoft.licensemanager.validation.FixedEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Transient
    private Long productId;

    @NotNull
    @Transient
    private Long userId;

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
    private Long entitlement;  // Perpetual: Not applicable, Trial|Annual: No of active Users

    private String entitlementType;

    public License() {
        super();
    }

    public License(Long productId, Long userId, String licenseType, String licenseFlavour, Integer validity, Long entitlement) {
        this.productId = productId;
        this.userId = userId;
        this.licenseType = licenseType;
        this.licenseFlavour = licenseFlavour;
        this.validity = validity;
        this.entitlement = entitlement;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(Long entitlement) {
        this.entitlement = entitlement;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLicenseFlavour() {
        return licenseFlavour;
    }

    public void setLicenseFlavour(String licenseFlavour) {
        this.licenseFlavour = licenseFlavour;
    }

    public String getEntitlementType() {
        return entitlementType;
    }

    public void setEntitlementType(String entitlementType) {
        this.entitlementType = entitlementType;
    }
}
