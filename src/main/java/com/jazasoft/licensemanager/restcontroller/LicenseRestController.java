package com.jazasoft.licensemanager.restcontroller;

import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.assembler.LicenseAssembler;
import com.jazasoft.licensemanager.dto.RestError;
import com.jazasoft.licensemanager.entity.License;
import com.jazasoft.licensemanager.entity.User;
import com.jazasoft.licensemanager.service.LicenseService;
import com.jazasoft.licensemanager.service.MyUserDetailsService;
import com.jazasoft.licensemanager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_LICENSES)
public class LicenseRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(LicenseRestController.class);

    @Autowired LicenseService licenseService;

    @Autowired ProductService productService;

    @Autowired MyUserDetailsService userDetailsService;

    @Autowired
    LicenseAssembler licenseAssembler;

    @GetMapping
    public ResponseEntity<?> listAllLicenses() {
        LOGGER.debug("listAllLicenses()");
        List<License> licenses = licenseService.findAll();
        Resources resources = new Resources(licenseAssembler.toResources(licenses), linkTo(LicenseRestController.class).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_LICENSES_LICENSE)
    public ResponseEntity<?> getLicense(@PathVariable("licenseId") long id) {
        LOGGER.debug("getLicense(): id = {}",id);
        License license = licenseService.findOne(id);
        if (license == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(licenseAssembler.toResource(license), HttpStatus.OK);
    }

    @PatchMapping(ApiUrls.URL_LICENSES_ACTIVATE)
    public ResponseEntity<?> activateLicense(@RequestParam("username") String username, @RequestParam("productCode") String productCode, @RequestParam("productKey") String productKey) {
        LOGGER.debug("checkLicense(): productCode = {}, productKey = {}",productCode, productKey);
        User user = userDetailsService.findByUsername(username);
        if (user == null) {
            user = userDetailsService.findByEmail(username);
            if (user == null) {
                Map<String, Object> resp = new HashMap<>();
                resp.put("status","USER_NOT_FOUND");
                resp.put("message", "User not found");
                return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
            }
        }
        License license = licenseService.activate(user, productCode, productKey);
        if (license != null) {
            return new ResponseEntity<>(license, HttpStatus.OK);
        } else {
            Map<String, Object> resp = new HashMap<>();
            resp.put("status","PRODUCT_ACTIVATION_FAILED");
            resp.put("message", "Invalid Product Key");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @GetMapping(ApiUrls.URL_LICENSES_CHECK)
    public ResponseEntity<?> checkLicense(@RequestParam("productCode") String productCode, @RequestParam("productKey") String productKey) {
        LOGGER.debug("checkLicense(): productCode = {}, productKey = {}",productCode, productKey);
        License license = licenseService.validate(productCode, productKey);
        if (license == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(licenseAssembler.toResource(license), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLicense(@Valid @RequestBody License license) {
        LOGGER.debug("createLicense():\n {}", license.toString());
        if (!productService.exists(license.getProductId())){
            RestError error = new RestError(404,40401,"Product with id = " + license.getProductId() + " not found","","");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);

        }else if (!userDetailsService.exists(license.getUserId())) {
            RestError error = new RestError(404,40401,"User with id = " + license.getUserId() + " not found","","");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        license = licenseService.save(license);
        Link selfLink = linkTo(LicenseRestController.class).slash(license.getId()).withSelfRel();
        return ResponseEntity.created(URI.create(selfLink.getHref())).build();
    }

    @PutMapping(ApiUrls.URL_LICENSES_LICENSE)
    public ResponseEntity<?> updateLicense(@PathVariable("licenseId") long id,@Validated @RequestBody License license) {
        LOGGER.debug("updateLicense(): id = {} \n {}",id,license);
        if (!licenseService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        license.setId(id);
        license = licenseService.update(license);
        return new ResponseEntity<>(licenseAssembler.toResource(license), HttpStatus.OK);
    }


}
