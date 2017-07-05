package com.jazasoft.licensemanager.restcontroller;

/**
 * Created by mdzahidraza on 23/06/17.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.dto.OauthResponse;
import com.jazasoft.licensemanager.entity.Product;
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
//@Ignore
public class ProductIntegrationTest {

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
    public void getAllProduct() throws Exception {
        this.mvc.perform(get(ApiUrls.ROOT_URL_PRODUCTS).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.products", hasSize(3)))  //it is executing in last, so one is added by create product test
                .andExpect(jsonPath("$._embedded.products[0].name", is("Time And Action Calender")))
                .andExpect(jsonPath("$._embedded.products[0]._links.self").exists());
    }


    @Test
    public void getProduct() throws Exception {
        this.mvc.perform(get(ApiUrls.ROOT_URL_PRODUCTS + "/1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is("Time And Action Calender")))
                .andExpect(jsonPath("$._links.self").exists());

        this.mvc.perform(get(ApiUrls.ROOT_URL_PRODUCTS + "/10").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }


    @Test
    public void createAndDeleteProduct() throws Exception {
        Product product = new Product("Test Product", "test description", "TST");
        System.out.println("-$$$-" + mapper.writeValueAsString(product));
        MvcResult mvcResult = mvc
                .perform(post(ApiUrls.ROOT_URL_PRODUCTS)
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String locationUri = mvcResult.getResponse().getHeader("Location");
        assertTrue(locationUri.contains(ApiUrls.ROOT_URL_PRODUCTS));

        int idx = locationUri.lastIndexOf('/');
        String id = locationUri.substring(idx + 1);

        this.mvc.perform(get(ApiUrls.ROOT_URL_PRODUCTS + "/{id}", id).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is("Test Product")));

    }

    @Test
    public void createProductBadRequest() throws Exception {
        Product product = new Product();
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_PRODUCTS)
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)));

        //Test each fields one by one
        product = new Product("", "test decription", "ANDON");
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_PRODUCTS)
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].message", containsString("length must be between 3")));

        product = new Product("Md Zahid Raza", "test product", "");
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_PRODUCTS)
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("productPrefix")));

    }

}

