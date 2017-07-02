package com.jazasoft.licensemanager.assembler;

import com.jazasoft.licensemanager.entity.License;
import com.jazasoft.licensemanager.restcontroller.LicenseRestController;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class LicenseAssembler extends ResourceAssemblerSupport<License, Resource>{

    public LicenseAssembler(){
        super(LicenseRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(License license) {
        return new Resource<>(license, linkTo(methodOn(LicenseRestController.class).getLicense(license.getId())).withSelfRel());
    }

    @Override
    public List<Resource> toResources(Iterable<? extends License> users) {
        List<Resource> resources = new ArrayList<>();
        for(License user : users) {
            resources.add(toResource(user));
        }
        return resources;
    }

}
