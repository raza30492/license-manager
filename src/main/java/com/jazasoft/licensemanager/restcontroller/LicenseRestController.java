package com.jazasoft.licensemanager.restcontroller;

import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.assembler.LicenseAssembler;
import com.jazasoft.licensemanager.entity.License;
import com.jazasoft.licensemanager.service.LicenseService;
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
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_LICENSES)
public class LicenseRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(LicenseRestController.class);

    @Autowired
    private LicenseService licenseService;

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

    @PostMapping
    public ResponseEntity<Void> createLicense(@Valid @RequestBody License license) {
        LOGGER.debug("createLicense():\n {}", license.toString());
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
