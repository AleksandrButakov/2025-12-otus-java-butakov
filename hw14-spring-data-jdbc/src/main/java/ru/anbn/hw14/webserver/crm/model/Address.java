package ru.anbn.hw14.webserver.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("address")
public class Address {

    @Id
    private Long id;

    private String street;

    public Address(String street) {
        this.street = street;
    }
}
