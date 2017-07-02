package com.jazasoft.licensemanager.respository;

import com.jazasoft.licensemanager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mdzahidraza on 03/07/17.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findOneByName(String name);
}
