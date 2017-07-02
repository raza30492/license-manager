package com.jazasoft.licensemanager.assembler;

import com.jazasoft.licensemanager.entity.Client;
import com.jazasoft.licensemanager.restcontroller.ClientRestController;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ClientAssembler extends ResourceAssemblerSupport<Client, Resource>{

    public ClientAssembler(){
        super(ClientRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(Client client) {
        return new Resource<>(client, linkTo(methodOn(ClientRestController.class).getClient(client.getId())).withSelfRel());
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Client> users) {
        List<Resource> resources = new ArrayList<>();
        for(Client user : users) {
            resources.add(toResource(user));
        }
        return resources;
    }

}
