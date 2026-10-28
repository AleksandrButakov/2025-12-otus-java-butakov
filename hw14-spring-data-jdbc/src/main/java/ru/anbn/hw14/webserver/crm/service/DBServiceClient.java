package ru.anbn.hw14.webserver.crm.service;

import java.util.List;
import java.util.Optional;
import ru.anbn.hw14.webserver.crm.model.Client;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    List<Client> findAllFull();
}
