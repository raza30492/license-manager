package com.jazasoft.licensemanager.service;

import com.jazasoft.licensemanager.entity.Product;
import com.jazasoft.licensemanager.respository.ProductRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@Service
@Transactional(readOnly = false)
public class ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    private Mapper mapper;

    public ProductService(ProductRepository productRepository, Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<Product> findAll() {
        LOGGER.debug("findAll");
        return productRepository.findAll();
    }

    public Product findOne(Long id) {
        LOGGER.debug("findOne: id = {}", id);
        return productRepository.findOne(id);
    }

    public Product findOneByName(String name) {
        LOGGER.debug("findOneByName: name = {}", name);
        return productRepository.findOneByName(name);
    }

    public boolean exists(Long id) {
        LOGGER.debug("exists: id = {}", id);
        return productRepository.exists(id);
    }

    @Transactional
    public Product save(Product product) {
        LOGGER.debug("save");
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Product product) {
        LOGGER.debug("update");
        Product product2 = productRepository.findOne(product.getId());
        mapper.map(product,product2);
        return product2;
    }
}
