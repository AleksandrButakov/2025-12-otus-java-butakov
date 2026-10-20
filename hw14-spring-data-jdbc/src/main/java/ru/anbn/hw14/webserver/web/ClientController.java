package ru.anbn.hw14.webserver.web;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.anbn.hw14.webserver.crm.model.Address;
import ru.anbn.hw14.webserver.crm.model.Client;
import ru.anbn.hw14.webserver.crm.model.Phone;
import ru.anbn.hw14.webserver.crm.service.DBServiceClient;

@Controller
public class ClientController {

    private final DBServiceClient dbServiceClient;

    public ClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    // Обрабатываем корневой путь "/" и "/admin"
    @GetMapping({"/", "/admin"})
    public String showClientsList(Model model) {
        var clients = dbServiceClient.findAllFull();
        // Модель используется для передачи данных в HTML-шаблон Thymeleaf
        model.addAttribute("clients", clients);
        return "admin"; // Имя файла-шаблона без расширения .html (ищется в src/main/resources/templates/)
    }

    // Обрабатываем POST-запрос на создание клиента
    @PostMapping("/admin")
    public String createClient(
            @RequestParam("name") String name,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "phones", required = false) String phonesParam) {
        Client client = new Client();
        client.setName(name);

        if (street != null && !street.isBlank()) {
            client.setAddress(new Address(street.trim()));
        }

        if (phonesParam != null && !phonesParam.isBlank()) {
            Arrays.stream(phonesParam.split(","))
                    .map(String::trim)
                    .filter(p -> !p.isEmpty())
                    .forEach(phoneNumber -> client.getPhones().add(new Phone(phoneNumber)));
        }

        dbServiceClient.saveClient(client);

        // Перенаправляем на GET-метод, чтобы предотвратить повторную отправку формы при F5 (паттерн POST/Redirect/GET)
        return "redirect:/admin";
    }
}
