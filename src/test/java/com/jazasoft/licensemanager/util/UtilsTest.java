package com.jazasoft.licensemanager.util;

import org.junit.Test;

/**
 * Created by mdzahidraza on 03/07/17.
 */
public class UtilsTest {

    @Test
    public void testRandomNumber() {
        System.out.println(Utils.getRandomNumber(6));
        System.out.println(Utils.getRandomNumber(6));
        System.out.println(Utils.getRandomNumber(6));
        System.out.println(Utils.getRandomNumber(6));
    }

    @Test
    public void testRandomString() {
        System.out.println(Utils.getRandomString(4));
        System.out.println(Utils.getRandomString(4));
        System.out.println(Utils.getRandomString(4));
        System.out.println(Utils.getRandomString(4));
    }
}
