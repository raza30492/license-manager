package com.jazasoft.licensemanager.util;

import com.jazasoft.licensemanager.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mdzahidraza on 30/06/17.
 */
public class Utils {

    public static String getAppHome() {
        return System.getenv("SAMPLE_HOME");
    }

    public static Set<Role> getRoles(String roles) {
        if (roles == null) return null;
        String[] rols = roles.split(",");
        Set<Role> roleList = new HashSet<>();
        for (String role: rols) {
            roleList.add(Role.parse(role));
        }
        return roleList;
    }
}
