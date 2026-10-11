package ru.anbn.hw11.cachehw;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw11.crm.model.Client;
import ru.anbn.hw11.crm.service.DBServiceClient;

public class CachedDbServiceClient implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(CachedDbServiceClient.class);

    private final DBServiceClient databaseService;
    private final HwCache<String, Client> cache;

    public CachedDbServiceClient(DBServiceClient databaseService, HwCache<String, Client> cache) {
        this.databaseService = databaseService;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        Client savedClient = databaseService.saveClient(client);

        // Кэшируем клонированную копию, чтобы избежать мутаций данных извне (как в демо str)
        String cacheKey = String.valueOf(savedClient.getId());
        cache.put(cacheKey, savedClient.clone());

        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        String cacheKey = String.valueOf(id);

        // 1. Пробуем достать из кэша
        Client cachedClient = cache.get(cacheKey);
        if (cachedClient != null) {
            log.info("[CACHE HIT] Client with id {} fetched from MyCache!", id);
            // Возвращаем клон, чтобы никто случайно не испортил состояние объекта внутри кэша
            return Optional.of(cachedClient.clone());
        }

        // 2. Если в кэше нет — идем в реальную базу данных
        log.info("[CACHE MISS] Client with id {} not found in cache. Accessing Database...", id);
        Optional<Client> dbClient = databaseService.getClient(id);

        // 3. Кладем в кэш найденного клиента
        dbClient.ifPresent(client -> cache.put(cacheKey, client.clone()));

        return dbClient;
    }

    @Override
    public List<Client> findAll() {
        // Выборки списков обычно не кэшируются в Key-Value хранилищах, делегируем базе
        return databaseService.findAll();
    }
}
