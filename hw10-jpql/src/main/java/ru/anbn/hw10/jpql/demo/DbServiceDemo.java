package ru.anbn.hw10.jpql.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw10.jpql.core.repository.DataTemplateHibernate;
import ru.anbn.hw10.jpql.core.repository.HibernateUtils;
import ru.anbn.hw10.jpql.core.sessionmanager.TransactionManagerHibernate;
import ru.anbn.hw10.jpql.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.anbn.hw10.jpql.crm.model.Address;
import ru.anbn.hw10.jpql.crm.model.Client;
import ru.anbn.hw10.jpql.crm.model.Phone;
import ru.anbn.hw10.jpql.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        Client client = new Client();
        client.setName("test");

        Phone phone1 = new Phone();
        phone1.setNumber("111");

        Phone phone2 = new Phone();
        phone2.setNumber("222");

        client.addPhone(phone1);
        client.addPhone(phone2);

        Address address = new Address();
        address.setStreet("Street 1");

        client.setAddress(address);

        dbServiceClient.saveClient(client);

        var clientSecond = dbServiceClient.saveClient(new Client(null, "dbServiceSecond"));

        var clientSecondSelected = dbServiceClient
                .getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));

        log.info("clientSecondSelected:{}", clientSecondSelected);

        clientSecondSelected.setName("dbServiceSecondUpdated");

        dbServiceClient.saveClient(clientSecondSelected);

        var clientUpdated = dbServiceClient
                .getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));

        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");

        for (Client client1 : dbServiceClient.findAllFull()) {
            log.info("client:{}", client1);
        }
    }
}
