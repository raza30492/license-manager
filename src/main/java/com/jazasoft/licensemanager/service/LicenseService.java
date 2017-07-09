package com.jazasoft.licensemanager.service;

import com.jazasoft.licensemanager.entity.Client;
import com.jazasoft.licensemanager.entity.License;
import com.jazasoft.licensemanager.entity.Product;
import com.jazasoft.licensemanager.respository.ClientRepository;
import com.jazasoft.licensemanager.respository.LicenseRepository;
import com.jazasoft.licensemanager.respository.ProductRepository;
import com.jazasoft.licensemanager.util.Utils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@Service
@Transactional(readOnly = false)
public class LicenseService {
    private final Logger LOGGER = LoggerFactory.getLogger(LicenseService.class);

    private LicenseRepository licenseRepository;

    private ProductRepository productRepository;

    private ClientRepository clientRepository;

    private Mapper mapper;

    public LicenseService(LicenseRepository licenseRepository, ProductRepository productRepository, ClientRepository clientRepository, Mapper mapper) {
        this.licenseRepository = licenseRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    public List<License> findAll() {
        LOGGER.debug("findAll");
        return licenseRepository.findAll();
    }

    public License findOne(Long id) {
        LOGGER.debug("findOne: id = {}", id);
        return licenseRepository.findOne(id);
    }

    public boolean exists(Long id) {
        LOGGER.debug("exists: id = {}", id);
        return licenseRepository.exists(id);
    }

    public License validate(String productCode, String productKey) {
        LOGGER.debug("validate");
        return licenseRepository.findOneByProductCodeAndProductKey(productCode, productKey);
    }

    @Transactional
    public License save(License license) {
        LOGGER.debug("save");
        Product product = productRepository.findOne(license.getProductId());
        Client client = clientRepository.findOne(license.getClientId());
        license.setProduct(product);
        license.setClient(client);
        license.setProductCode(product.getProductPrefix() + Utils.getRandomNumber(7));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(Utils.getRandomString(4)).append("-");
        }
        builder.setLength(builder.length()-1);
        license.setProductKey(builder.toString());
        license.setPurchasedOn(new Date());
        return licenseRepository.save(license);
    }

    @Transactional
    public License update(License license) {
        LOGGER.debug("update");
        License license2 = licenseRepository.findOne(license.getId());
        mapper.map(license,license2);
        return license2;
    }
}
