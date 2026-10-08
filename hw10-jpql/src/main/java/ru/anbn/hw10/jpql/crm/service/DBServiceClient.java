package ru.anbn.hw10.jpql.crm.service;

import java.util.List;
import java.util.Optional;
import ru.anbn.hw10.jpql.crm.model.Client;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
