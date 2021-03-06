package com.jazasoft.licensemanager.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by mdzahidraza on 02/07/17.
 */
public class YamlUtilsTest {

    private YamlUtils yamlUtils;
    File testFile;

    @Before
    public void setUp() {
        yamlUtils = YamlUtils.getINSTANCE();
        String filename = Utils.getAppHome() + File.separator + "conf" + File.separator + "test.yaml";
        testFile = new File(filename);
    }

    @Test
    public void testGetFileProprty() throws IOException{
        Assert.assertEquals("Md Zahid Raza",yamlUtils.getProperty(testFile, "name"));
        Assert.assertEquals("25",yamlUtils.getProperty(testFile, "age"));
        Assert.assertEquals("Bangalore",yamlUtils.getProperty(testFile, "address.city"));
        Assert.assertEquals("India",yamlUtils.getProperty(testFile, "address.country"));

        List list = (List)yamlUtils.getProperty(testFile, "phones");
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("8987525008", ((Map)list.get(0)).get("number"));
        Assert.assertEquals("8904360418", ((Map)list.get(1)).get("number"));
    }

    @Test
    public void testAppProperty() throws IOException{
        Assert.assertEquals("8082",yamlUtils.getAppProperty("server.port"));
    }

    @Test
    public void testNestedProperty() throws IOException{
        Map schema = (Map)yamlUtils.getProperty(testFile,"schema");
        Assert.assertEquals("schema-mysql.sql", yamlUtils.getNestedProperty(schema,"mysql.init.filename"));
        Assert.assertEquals("schema-pg.sql", yamlUtils.getNestedProperty(schema,"postgresql.init.filename"));
    }

}
