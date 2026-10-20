package ru.anbn.hw14.webserver.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("phone")
public class Phone {

    @Id
    private Long id;

    private String number;

    public Phone(String number) {
        this.number = number;
    }
}
