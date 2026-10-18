package ru.anbn.hw12.webserver.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.anbn.hw12.webserver.crm.model.Address;
import ru.anbn.hw12.webserver.crm.model.Client;
import ru.anbn.hw12.webserver.crm.model.Phone;
import ru.anbn.hw12.webserver.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessorImpl templateProcessor;

    public AdminServlet(TemplateProcessorImpl templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Защита страницы: проверяем наличие сессии администратора
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }

        resp.setContentType("text/html;charset=utf-8");

        // Получаем список клиентов с помощью JOIN FETCH метода
        var clients = dbServiceClient.findAllFull();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("clients", clients);

        String page = templateProcessor.getPage("admin.ftl", pageVariables);
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Защита на POST-запросы
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        req.setCharacterEncoding("UTF-8");

        // Достаем параметры из HTML-формы шаблона admin.ftl
        String name = req.getParameter("name");
        String street = req.getParameter("street");
        String phonesParam = req.getParameter("phones");

        // Формируем сущность Client
        Client client = new Client();
        client.setName(name);

        // Наполняем адресом, если он указан
        if (street != null && !street.isBlank()) {
            Address address = new Address();
            address.setStreet(street);
            client.setAddress(address);
        }

        // Парсим телефоны, введенные через запятую, и связываем их с клиентом
        if (phonesParam != null && !phonesParam.isBlank()) {
            Arrays.stream(phonesParam.split(","))
                    .map(String::trim)
                    .filter(p -> !p.isEmpty())
                    .forEach(phoneNumber -> {
                        Phone phone = new Phone();
                        phone.setNumber(phoneNumber);
                        client.addPhone(phone);
                    });
        }

        // Сохраняем агрегат через рабочую сессию Hibernate
        dbServiceClient.saveClient(client);

        /*
         Перенаправляем пользователя на GET /admin, чтобы предотвратить повторную отправку формы при обновлении
         страницы F5
         */
        resp.sendRedirect("/admin");
    }
}
