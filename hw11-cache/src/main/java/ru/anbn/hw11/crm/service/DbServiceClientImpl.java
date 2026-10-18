package ru.anbn.hw11.crm.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw11.core.repository.DataTemplate;
import ru.anbn.hw11.core.sessionmanager.TransactionManager;
import ru.anbn.hw11.crm.model.Client;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, client);
                log.info("created client: {}", client);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, client);
            log.info("updated client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }

    public List<Client> findAllFull() {
        return transactionManager.doInReadOnlyTransaction(session -> session.createQuery(
                        "select distinct c from Client c " + "left join fetch c.address " + "left join fetch c.phones",
                        Client.class)
                .getResultList());
    }
}
