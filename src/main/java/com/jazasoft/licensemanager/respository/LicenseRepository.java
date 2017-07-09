package com.jazasoft.licensemanager.respository;

import com.jazasoft.licensemanager.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mdzahidraza on 03/07/17.
 */
public interface LicenseRepository extends JpaRepository<License, Long> {

    License findOneByProductCodeAndProductKey(String productCode, String productKey);
}
