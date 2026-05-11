package ru.anbn.hw10.jpql.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;

        if (address != null) {
            setAddress(address);
        }

        if (phones != null) {
            phones.forEach(this::addPhone);
        }
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setClient(this);
    }

    public void setAddress(Address address) {
        this.address = address;
        address.setClient(this);
    }

    @Override
    public Client clone() {
        Client cloned = new Client();
        cloned.id = this.id;
        cloned.name = this.name;

        if (this.address != null) {
            Address clonedAddress = new Address();
            clonedAddress.setId(this.address.getId());
            clonedAddress.setStreet(this.address.getStreet());

            clonedAddress.setClient(cloned);

            cloned.address = clonedAddress;
        }

        if (this.phones != null) {
            List<Phone> clonedPhones = new java.util.ArrayList<>();

            for (Phone phone : this.phones) {
                Phone clonedPhone = new Phone();
                clonedPhone.setId(phone.getId());
                clonedPhone.setNumber(phone.getNumber());

                clonedPhone.setClient(cloned);
                clonedPhones.add(clonedPhone);
            }

            cloned.phones = clonedPhones;
        }

        return cloned;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
