package com.jazasoft.licensemanager;

/**
 * Created by mdzahidraza on 11/07/17.
 */
public enum EntitlementType {
    USERS("USERS");

    private String value;

    EntitlementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EntitlementType parse(String value) {
        EntitlementType entitlementType = null;
        for (EntitlementType item : EntitlementType.values()) {
            if (item.getValue().equals(value)) {
                entitlementType = item;
                break;
            }
        }
        return entitlementType;
    }

    @Override
    public String toString() {
        return value;
    }
}
