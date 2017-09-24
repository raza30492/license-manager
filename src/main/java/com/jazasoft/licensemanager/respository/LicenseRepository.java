package com.jazasoft.licensemanager.respository;

import com.jazasoft.licensemanager.entity.License;
import com.jazasoft.licensemanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mdzahidraza on 03/07/17.
 */
public interface LicenseRepository extends JpaRepository<License, Long> {

    License findOneByProductCodeAndProductKey(String productCode, String productKey);

    List<License> findByUser(User user);
}
