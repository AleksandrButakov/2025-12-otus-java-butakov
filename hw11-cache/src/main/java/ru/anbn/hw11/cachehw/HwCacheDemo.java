package ru.anbn.hw11.cachehw;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw11.core.repository.DataTemplateHibernate;
import ru.anbn.hw11.core.repository.HibernateUtils;
import ru.anbn.hw11.core.sessionmanager.TransactionManagerHibernate;
import ru.anbn.hw11.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.anbn.hw11.crm.model.Address;
import ru.anbn.hw11.crm.model.Client;
import ru.anbn.hw11.crm.model.Phone;
import ru.anbn.hw11.crm.service.DBServiceClient;
import ru.anbn.hw11.crm.service.DbServiceClientImpl;

import java.util.Optional;

public class HwCacheDemo {
    private static final Logger log = LoggerFactory.getLogger(HwCacheDemo.class);

    public static void main(String[] args) {
        new HwCacheDemo().demo();
    }

    private void demo() {
        // 1. Инициализация базы данных.
        var configuration = new Configuration().configure("hibernate.cfg.xml");

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        // Базовый сервис СУБД.
        DBServiceClient dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        // 2. Инициализация кэша и слушателя.
        HwCache<String, Client> cache = new MyCache<>();

        HwListener<String, Client> listener =
                (key, value, action) -> log.info("[LISTENER NOTIFICATION] key:{}, action: {}", key, action);

        cache.addListener(listener);

        // 3. Оборачиваем сервис базы данных в кэширующий декоратор.
        DBServiceClient cachedService = new CachedDbServiceClient(dbServiceClient, cache);

        // 4. Тест: Создаем и сохраняем клиента.
        Client client = new Client("Ivan");
        client.addPhone(new Phone(null, "+7-999-111-22-33"));
        client.setAddress(new Address(null, "Lenina street"));

        Client savedClient = cachedService.saveClient(client);
        long clientId = savedClient.getId();

        log.info("*** СТАРТ ПРОВЕРКИ СКОРОСТИ ***");

        // Первый запрос: Объект уже попал в кэш во время сохранения (saveClient)
        long startTime = System.nanoTime();
        Optional<Client> firstLoad = cachedService.getClient(clientId);
        long firstTime = System.nanoTime() - startTime;
        log.info("Время первого запроса (из КЭША): {} нс", firstTime);
        // В логах НЕ должно быть SQL запроса от Hibernate, только лог [CACHE HIT]

        // Очистим кэш вручную, чтобы запрос пошел в БД.
        cache.remove(String.valueOf(clientId));

        // Второй запрос: Кэш пуст, сервис вынужден пойти в БД.
        startTime = System.nanoTime();
        Optional<Client> secondLoad = cachedService.getClient(clientId);
        long secondTime = System.nanoTime() - startTime;
        log.info("Время второго запроса (из БАЗЫ): {} нс", secondTime);
        // В логах ПОЯВИТСЯ SQL лог Hibernate: select...

        log.info("*** КЭШ БЫСТРЕЕ БАЗЫ В {} РАЗ(А) ***", (secondTime / firstTime));

        sessionFactory.close();
    }
}
