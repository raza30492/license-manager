package com.jazasoft.licensemanager.assembler;

import com.jazasoft.licensemanager.entity.Product;
import com.jazasoft.licensemanager.restcontroller.ProductRestController;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductAssembler extends ResourceAssemblerSupport<Product, Resource>{

    public ProductAssembler(){
        super(ProductRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(Product userDto) {
        return new Resource<>(userDto, linkTo(methodOn(ProductRestController.class).getProduct(userDto.getId())).withSelfRel());
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Product> users) {
        List<Resource> resources = new ArrayList<>();
        for(Product user : users) {
            resources.add(toResource(user));
        }
        return resources;
    }

}
