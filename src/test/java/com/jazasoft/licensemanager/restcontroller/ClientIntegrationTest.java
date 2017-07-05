package com.jazasoft.licensemanager.restcontroller;

/**
 * Created by mdzahidraza on 23/06/17.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.dto.OauthResponse;
import com.jazasoft.licensemanager.entity.Address;
import com.jazasoft.licensemanager.entity.Client;
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
public class ClientIntegrationTest {

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
    public void getAllClient() throws Exception {
        this.mvc.perform(get(ApiUrls.ROOT_URL_CLIENTS).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.clients", hasSize(1)))  //it is executing in last, so one is added by create client test
                .andExpect(jsonPath("$._embedded.clients[0].name", is("Laguna Clothing Pvt. Ltd.")))
                .andExpect(jsonPath("$._embedded.clients[0]._links.self").exists());
    }


    @Test
    public void getClient() throws Exception {
        this.mvc.perform(get(ApiUrls.ROOT_URL_CLIENTS + "/1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is("Laguna Clothing Pvt. Ltd.")))
                .andExpect(jsonPath("$._links.self").exists());

        this.mvc.perform(get(ApiUrls.ROOT_URL_CLIENTS + "/10").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }


    @Test
    public void createAndDeleteClient() throws Exception {
        Client client = new Client("Test Client", "client@gmail.com");
        Address address = new Address("#50, JP Nagar", "Bangalore", "560047", "India");
        client.setAddress(address);
        MvcResult mvcResult = mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String locationUri = mvcResult.getResponse().getHeader("Location");
        assertTrue(locationUri.contains(ApiUrls.ROOT_URL_CLIENTS));

        int idx = locationUri.lastIndexOf('/');
        String id = locationUri.substring(idx + 1);

        this.mvc.perform(get(ApiUrls.ROOT_URL_CLIENTS + "/{id}", id).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is("Test Client")));

        this.mvc.perform(delete(ApiUrls.ROOT_URL_CLIENTS + "/{id}", id).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNoContent());

        this.mvc.perform(get(ApiUrls.ROOT_URL_CLIENTS + "/{id}", id).header("Authorization", "Bearer " + accessToken))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    @Test
    public void createClientBadRequest() throws Exception {
        Client client = new Client();
        Address address = new Address();
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(6)));

        //Test each fields one by one
        //Test Client Name
        client = new Client("", "client@gmail.com");
        address = new Address("#50, JP Nagar", "Bangalore", "560047", "India");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].message", containsString("length must be between 3")));

        //Test Client Email
        client = new Client("Laguna Clothing Private Limited", "client");
        address = new Address("#50, JP Nagar", "Bangalore", "560047", "India");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].message", containsString("Incorrect email id")));

        //Test Client Email
        client = new Client("Laguna Clothing Private Limited", "");
        address = new Address("#50, JP Nagar", "Bangalore", "560047", "India");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].message", containsString("Incorrect email id")));

        //Test Client Address sreet
        client = new Client("Laguna Clothing Private Limited", "client@gmail.com");
        address = new Address("", "Bangalore", "560047", "India");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("address.street")))
                .andExpect(jsonPath("$[0].message", containsString("length must be between 5")));

        //Test Client Address City
        client = new Client("Laguna Clothing Pvt. Ltd.", "client@gmail.com");
        address = new Address("#50, JP Nagar", "", "560047", "India");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("address.city")))
                .andExpect(jsonPath("$[0].message", containsString("length must be between 3")));

        //Test Client Address zip code
        client = new Client("Laguna Clothing Pvt. Ltd.", "client@gmail.com");
        address = new Address("#50, JP Nagar", "Bangalore", "", "India");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("address.zipCode")))
                .andExpect(jsonPath("$[0].message", containsString("zip/pin length must be 6 digit number")));

        //Test Client Address Country
        client = new Client("Laguna Clothing Pvt. Ltd.", "client@gmail.com");
        address = new Address("#50, JP Nagar", "Bangalore", "560047", "");
        client.setAddress(address);
        this.mvc
                .perform(post(ApiUrls.ROOT_URL_CLIENTS)
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].field", is("address.country")))
                .andExpect(jsonPath("$[0].message", containsString("length must be between 3")));

    }

}

