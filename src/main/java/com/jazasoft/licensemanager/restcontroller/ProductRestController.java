package com.jazasoft.licensemanager.restcontroller;

import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.assembler.ProductAssembler;
import com.jazasoft.licensemanager.entity.Product;
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
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_PRODUCTS)
public class ProductRestController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);
    
    @Autowired
    private ProductService productService;

    @Autowired
    ProductAssembler productAssembler;

    @GetMapping
    public ResponseEntity<?> listAllProducts() {
        LOGGER.debug("listAllProducts()");
        List<Product> products = productService.findAll();
        Resources resources = new Resources(productAssembler.toResources(products), linkTo(ProductRestController.class).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_PRODUCTS_PRODUCT)
    public ResponseEntity<?> getProduct(@PathVariable("productId") long id) {
        LOGGER.debug("getProduct(): id = {}",id);
        Product product = productService.findOne(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productAssembler.toResource(product), HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_PRODUCTS_PRODUCT_SEARCH_BY_NAME)
    public ResponseEntity<?> searchByName(@RequestParam("name") String name){
        LOGGER.debug("searchByName(): name = {}",name);
        Product product = productService.findOneByName(name);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productAssembler.toResource(product), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody Product product) {
        LOGGER.debug("createProduct():\n {}", product.toString());
        product = productService.save(product);
        Link selfLink = linkTo(ProductRestController.class).slash(product.getId()).withSelfRel();
        return ResponseEntity.created(URI.create(selfLink.getHref())).build();
    }

    @PutMapping(ApiUrls.URL_PRODUCTS_PRODUCT)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") long id,@Validated @RequestBody Product product) {
        LOGGER.debug("updateProduct(): id = {} \n {}",id,product);
        if (!productService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setId(id);
        product = productService.update(product);
        return new ResponseEntity<>(productAssembler.toResource(product), HttpStatus.OK);
    }
}
