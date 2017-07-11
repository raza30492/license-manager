package com.jazasoft.licensemanager.assembler;

import com.jazasoft.licensemanager.entity.Company;
import com.jazasoft.licensemanager.restcontroller.ClientRestController;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ClientAssembler extends ResourceAssemblerSupport<Company, Resource>{

    public ClientAssembler(){
        super(ClientRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(Company company) {
        return new Resource<>(company, linkTo(methodOn(ClientRestController.class).getClient(company.getId())).withSelfRel());
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Company> users) {
        List<Resource> resources = new ArrayList<>();
        for(Company user : users) {
            resources.add(toResource(user));
        }
        return resources;
    }

}
