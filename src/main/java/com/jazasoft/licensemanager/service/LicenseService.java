package com.jazasoft.licensemanager.service;

import com.jazasoft.licensemanager.entity.Company;
import com.jazasoft.licensemanager.entity.License;
import com.jazasoft.licensemanager.entity.Product;
import com.jazasoft.licensemanager.entity.User;
import com.jazasoft.licensemanager.exception.InvalidLicenseException;
import com.jazasoft.licensemanager.respository.UserRepository;
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

    private UserRepository userRepository;

    private Mapper mapper;

    public LicenseService(LicenseRepository licenseRepository, ProductRepository productRepository, UserRepository userRepository, Mapper mapper) {
        this.licenseRepository = licenseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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

    public License activate(User user, String productCode, String productKey) {
        List<License> licenses = licenseRepository.findByUser(user);
        if (licenses.size() == 0) {
            throw new InvalidLicenseException("No License found for user [" + user.getFirstName() + " " + user.getLastName() + "]");
        }
        License license = licenses.stream().filter(lic -> lic.getProductCode().equals(productCode)).findAny().orElse(null);
        if (license == null) {
            throw new InvalidLicenseException("User does not have License for product code [" + productCode + "]");
        }
        if (license.getProductKey().equals(productKey)) {
            if (!license.isActivated()) {
                license.setActivated(true);
                license.setActivatedOn(new Date());
            }
            license.setUserId(license.getUser().getId());
            license.setProductId(license.getProduct().getId());

            return license;
        }
        return null;
    }

    public License validate(String productCode, String productKey) {
        LOGGER.debug("validate");
        return licenseRepository.findOneByProductCodeAndProductKey(productCode, productKey);
    }

    @Transactional
    public License save(License license) {
        LOGGER.debug("save");
        Product product = productRepository.findOne(license.getProductId());
        User user = userRepository.findOne(license.getUserId());
        license.setUser(user);
        license.setProduct(product);
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
