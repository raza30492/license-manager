package com.jazasoft.licensemanager.restcontroller;

import com.jazasoft.licensemanager.ApiUrls;
import com.jazasoft.licensemanager.assembler.ClientAssembler;
import com.jazasoft.licensemanager.entity.Client;
import com.jazasoft.licensemanager.service.ClientService;
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
@RequestMapping(ApiUrls.ROOT_URL_CLIENTS)
public class ClientRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    ClientAssembler clientAssembler;

    @GetMapping
    public ResponseEntity<?> listAllClients() {
        LOGGER.debug("listAllClients()");
        List<Client> clients = clientService.findAll();
        Resources resources = new Resources(clientAssembler.toResources(clients), linkTo(ClientRestController.class).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_CLIENTS_CLIENT)
    public ResponseEntity<?> getClient(@PathVariable("clientId") long id) {
        LOGGER.debug("getClient(): id = {}",id);
        Client client = clientService.findOne(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientAssembler.toResource(client), HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_CLIENTS_CLIENT_SEARCH_BY_NAME)
    public ResponseEntity<?> searchByName(@RequestParam("name") String name){
        LOGGER.debug("searchByName(): name = {}",name);
        Client client = clientService.findOneByName(name);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientAssembler.toResource(client), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createClient(@Valid @RequestBody Client client) {
        LOGGER.debug("createClient():\n {}", client.toString());
        client = clientService.save(client);
        Link selfLink = linkTo(ClientRestController.class).slash(client.getId()).withSelfRel();
        return ResponseEntity.created(URI.create(selfLink.getHref())).build();
    }

    @PutMapping(ApiUrls.URL_CLIENTS_CLIENT)
    public ResponseEntity<?> updateClient(@PathVariable("clientId") long id,@Validated @RequestBody Client client) {
        LOGGER.debug("updateClient(): id = {} \n {}",id,client);
        if (!clientService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        client.setId(id);
        client = clientService.update(client);
        return new ResponseEntity<>(clientAssembler.toResource(client), HttpStatus.OK);
    }
}
