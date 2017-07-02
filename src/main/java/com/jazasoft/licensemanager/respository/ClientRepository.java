package com.jazasoft.licensemanager.respository;

import com.jazasoft.licensemanager.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mdzahidraza on 03/07/17.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
