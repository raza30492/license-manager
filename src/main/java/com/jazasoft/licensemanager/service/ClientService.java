package com.jazasoft.licensemanager.service;

import com.jazasoft.licensemanager.entity.Client;
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

    public List<Client> findAll() {
        LOGGER.debug("findAll");
        return clientRepository.findAll();
    }

    public Client findOne(Long id) {
        LOGGER.debug("findOne: id = {}", id);
        return clientRepository.findOne(id);
    }

    public Client findOneByName(String name) {
        LOGGER.debug("findOneByName: name = {}", name);
        return clientRepository.findOneByName(name);
    }

    public boolean exists(Long id) {
        LOGGER.debug("exists: id = {}", id);
        return clientRepository.exists(id);
    }

    @Transactional
    public Client save(Client client) {
        LOGGER.debug("save");
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Client client) {
        LOGGER.debug("update");
        Client client2 = clientRepository.findOne(client.getId());
        mapper.map(client,client2);
        return client2;
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("update");
        Client client = clientRepository.findOne(id);
        client.setEnabled(false);
    }

}
