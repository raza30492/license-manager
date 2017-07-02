package com.jazasoft.licensemanager.entity;

/**
 * Created by mdzahidraza on 03/07/17.
 */
public enum LicenseType {
    TRIAL("TRIAL"), ANNUAL("ANNUAL"), PERPETUAL("PERPETUAL");

    private String value;

    LicenseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LicenseType parse(String value) {
        LicenseType licenseType = null;
        for (LicenseType item : LicenseType.values()) {
            if (item.getValue().equals(value)) {
                licenseType = item;
                break;
            }
        }
        return licenseType;
    }

    @Override
    public String toString() {
        return value;
    }
}
