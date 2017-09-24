package com.jazasoft.licensemanager.restcontroller;

/**
 * Created by mdzahidraza on 23/06/17.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.dto.OauthResponse;
import com.jazasoft.licensemanager.entity.License;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@Ignore
public class LicenseIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private final String contentType = "application/hal+json;charset=UTF-8";

    private String accessToken = "";


    @Before
    public void setUp() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "zahid7292");
        params.add("password", "admin");
        MvcResult mvcResult = mvc
                .perform(post(ApiUrls.OAUTH_URL)
                        .params(params)
                        .header("Authorization", "Basic Y2xpZW50OnNlY3JldA==")
                )
                .andExpect(status().isOk())
                .andReturn();
        String resp = mvcResult.getResponse().getContentAsString();
        OauthResponse oauthResponse = mapper.readValue(resp, OauthResponse.class);
        accessToken = oauthResponse.getAccess_token();
    }

    @Test
    public void validateLicense() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("productCode", "TNA5765713");
        params.add("productKey", "RTRD-DEQV-XNVO-ORUC");
        this.mvc
                .perform(get(ApiUrls.ROOT_URL_LICENSES + ApiUrls.URL_LICENSES_CHECK)
                        .header("Authorization", "Bearer " + accessToken)
                        .params(params)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.licenseType", is("TRIAL")))
                .andExpect(jsonPath("$.licenseFlavour", is("EXPRESS")))
                .andExpect(jsonPath("$._links.self").exists());

        //Invalid Product Key
        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add("productCode", "TNA5765713");
        params2.add("productKey", "RTRD-DEQV-XNVO-ORMC");
        this.mvc
                .perform(get(ApiUrls.ROOT_URL_LICENSES + ApiUrls.URL_LICENSES_CHECK)
                        .header("Authorization", "Bearer " + accessToken)
                        .params(params2)
                )
                .andExpect(status().isNotFound());

        //Invalid Product
        MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>();
        params3.add("productCode", "TNA576571376");
        params3.add("productKey", "RTRD-DEQV-XNVO-ORUC");
        this.mvc
                .perform(get(ApiUrls.ROOT_URL_LICENSES + ApiUrls.URL_LICENSES_CHECK)
                        .header("Authorization", "Bearer " + accessToken)
                        .params(params3)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllLicense() throws Exception {
        this.mvc.perform(get(ApiUrls.ROOT_URL_LICENSES).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.licenses", hasSize(3)))  //it is executing in last, so one is added by create license test
                .andExpect(jsonPath("$._embedded.licenses[0].licenseType", is("TRIAL")))
                .andExpect(jsonPath("$._embedded.licenses[0].licenseFlavour", is("EXPRESS")))
                .andExpect(jsonPath("$._embedded.licenses[0]._links.self").exists());
    }


    @Test
    public void getLicense() throws Exception {
        this.mvc.perform(get(ApiUrls.ROOT_URL_LICENSES + "/1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.licenseType", is("TRIAL")))
                .andExpect(jsonPath("$.licenseFlavour", is("EXPRESS")))
                .andExpect(jsonPath("$._links.self").exists());

        this.mvc.perform(get(ApiUrls.ROOT_URL_LICENSES + "/10").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }


    @Test
    public void createAndDeleteLicense() throws Exception {
        License license = new License(1L,1L,"TRIAL","ULTIMATE",30,20L);

        MvcResult mvcResult = mvc
                .perform(post(ApiUrls.ROOT_URL_LICENSES)
                        .content(mapper.writeValueAsString(license))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String locationUri = mvcResult.getResponse().getHeader("Location");
        assertTrue(locationUri.contains(ApiUrls.ROOT_URL_LICENSES));

        int idx = locationUri.lastIndexOf('/');
        String id = locationUri.substring(idx + 1);

        this.mvc.perform(get(ApiUrls.ROOT_URL_LICENSES + "/{id}", id).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.licenseType", is("TRIAL")))
                .andExpect(jsonPath("$.licenseFlavour", is("ULTIMATE")));

    }

    @Test
    public void createLicenseBadRequest() throws Exception {
        License license = new License();

        this.mvc
                .perform(post(ApiUrls.ROOT_URL_LICENSES)
                        .content(mapper.writeValueAsString(license))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(6)));

        //Test each fields one by one
        //Test License Name
//        license = new License();
//        this.mvc
//                .perform(post(ApiUrls.ROOT_URL_LICENSES)
//                        .content(mapper.writeValueAsString(license))
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .header("Authorization", "Bearer " + accessToken)
//                )
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].field", is("name")))
//                .andExpect(jsonPath("$[0].message", containsString("length must be between 3")));


    }



}

