package ru.anbn.hw14.webserver.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anbn.hw14.webserver.crm.model.Client;
import ru.anbn.hw14.webserver.crm.repository.ClientRepository;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final ClientRepository clientRepository;

    // Spring автоматически внедрит сюда репозиторий через конструктор
    public DbServiceClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional // Означает, что метод выполнится внутри транзакции базы данных
    public Client saveClient(Client client) {
        var savedClient = clientRepository.save(client);
        log.info("saved client: {}", savedClient);
        return savedClient;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList: {}", clientList);
        return clientList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAllFull() {
        /*
        В Spring Data JDBC вызов findAll() по умолчанию загружает весь агрегат целиком
        вместе с его адресом и списком телефонов за один проход.
        Поэтому здесь мы просто переиспользуем метод findAll()
        */

        return findAll();
    }
}
