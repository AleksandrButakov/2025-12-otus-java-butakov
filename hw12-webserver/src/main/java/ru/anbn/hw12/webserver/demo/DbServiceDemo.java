package ru.anbn.hw12.webserver.demo;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw12.webserver.core.repository.DataTemplateHibernate;
import ru.anbn.hw12.webserver.core.repository.HibernateUtils;
import ru.anbn.hw12.webserver.core.sessionmanager.TransactionManagerHibernate;
import ru.anbn.hw12.webserver.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.anbn.hw12.webserver.crm.model.Address;
import ru.anbn.hw12.webserver.crm.model.Client;
import ru.anbn.hw12.webserver.crm.model.Phone;
import ru.anbn.hw12.webserver.crm.service.DbServiceClientImpl;
import ru.anbn.hw12.webserver.web.AdminServlet;
import ru.anbn.hw12.webserver.web.LoginServlet;
import ru.anbn.hw12.webserver.web.TemplateProcessorImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final int WEB_SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {
        // 1. Инициализация бэкенда (Оригинальный рабочий код Hibernate из hw10)
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

        log.info("Бэкенд Hibernate успешно инициализирован.");

        // 2. Инициализация инфраструктуры веб-сервера
        TemplateProcessorImpl templateProcessor = new TemplateProcessorImpl();
        Server server = new Server(WEB_SERVER_PORT);

        // ServletContextHandler.SESSIONS включает поддержку HttpSession (сессий) в Jetty
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Регистрируем сервлеты и связываем их с URL-путями
        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor)), "/login");
        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor, dbServiceClient)), "/admin");

        // Стартовая страница "/" будет также обрабатываться LoginServlet (он перенаправит на /login)
        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor)), "/");

        server.setHandler(context);

        // 3. Запуск Jetty
        log.info("Запуск веб-сервера Jetty на порту {}", WEB_SERVER_PORT);
        server.start();
        log.info("Сервер запущен! Перейди по ссылке: http://localhost:{}", WEB_SERVER_PORT);

        // join() заставляет главный поток Java ждать завершения работы веб-сервера
        server.join();
    }
}
