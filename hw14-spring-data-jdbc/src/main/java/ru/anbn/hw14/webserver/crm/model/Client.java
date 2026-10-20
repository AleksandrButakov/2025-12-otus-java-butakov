package ru.anbn.hw14.webserver.crm.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("client")
public class Client {

    @Id
    private Long id;

    private String name;

    // Мапим One-to-One связь по колонке client_id в таблице address
    @MappedCollection(idColumn = "client_id")
    private Address address;

    // Мапим One-to-Many связь. keyColumn указывает на созданную нами client_id_key
    @MappedCollection(idColumn = "client_id", keyColumn = "client_id_key")
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        if (phones != null) {
            this.phones.addAll(phones);
        }
    }
}
