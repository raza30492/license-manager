package com.jazasoft.licensemanager.entity;

/**
 * Created by mdzahidraza on 05/07/17.
 */
public enum LicenseFlavour {
    EXPRESS("EXPRESS"), PREMIUM("PREMIUM"), ULTIMATE("ULTIMATE");

    private String value;

    LicenseFlavour(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LicenseFlavour parse(String value) {
        LicenseFlavour licenseFlavour = null;
        for (LicenseFlavour item : LicenseFlavour.values()) {
            if (item.getValue().equals(value)) {
                licenseFlavour = item;
                break;
            }
        }
        return licenseFlavour;
    }

    @Override
    public String toString() {
        return value;
    }
}
