package com.jazasoft.licensemanager;

public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), CLIENT("ROLE_CLIENT");

    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role parse(String value) {
        Role role = null;
        for (Role item : Role.values()) {
            if (item.getValue().equals(value)) {
                role = item;
                break;
            }
        }
        return role;
    }
}
