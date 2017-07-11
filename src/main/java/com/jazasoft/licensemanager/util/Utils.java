package com.jazasoft.licensemanager.util;

import com.jazasoft.licensemanager.Role;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by mdzahidraza on 30/06/17.
 */
public class Utils {

    private static final char[] symbols;
    private static final char[] symbols2;
    private static final Random random = new Random();
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '1'; ch <= '9'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();

        tmp.setLength(0);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            tmp.append(ch);
        symbols2 = tmp.toString().toCharArray();
    }

    public static String getAppHome() {
        return System.getenv("LM_HOME");
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

    public static String getRandomNumber(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static String getRandomString(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols2[random.nextInt(symbols2.length)];
        return new String(buf);
    }
}
