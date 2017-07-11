package com.jazasoft.licensemanager.service;

import com.jazasoft.licensemanager.entity.Company;
import com.jazasoft.licensemanager.respository.ClientRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@Service
@Transactional(readOnly = false)
public class ClientService {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private ClientRepository clientRepository;

    private Mapper mapper;

    public ClientService(ClientRepository clientRepository, Mapper mapper) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    public List<Company> findAll() {
        LOGGER.debug("findAll");
        return clientRepository.findAll();
    }

    public Company findOne(Long id) {
        LOGGER.debug("findOne: id = {}", id);
        return clientRepository.findOne(id);
    }

    public Company findOneByName(String name) {
        LOGGER.debug("findOneByName: name = {}", name);
        return clientRepository.findOneByName(name);
    }

    public boolean exists(Long id) {
        LOGGER.debug("exists: id = {}", id);
        return clientRepository.exists(id);
    }

    @Transactional
    public Company save(Company company) {
        LOGGER.debug("save");
        return clientRepository.save(company);
    }

    @Transactional
    public Company update(Company company) {
        LOGGER.debug("update");
        Company company2 = clientRepository.findOne(company.getId());
        mapper.map(company, company2);
        return company2;
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("update");
        Company company = clientRepository.findOne(id);
    }

}
