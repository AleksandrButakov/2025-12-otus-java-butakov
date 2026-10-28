package ru.anbn.hw14.webserver.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.anbn.hw14.webserver.crm.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    /*
    Для информации. Никаких методов писать не нужно.
    Методы save(), findById(), findAll(), deleteById() уже доступны «из коробки».
    */
}
