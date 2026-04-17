package ru.anbn.crm.model;

import ru.anbn.crm.annotation.Id;

public class Manager {
    @Id
    private Long id;

    private String label;
    private String param1;

    public Manager() {}

    public Manager(String label) {
        this.label = label;
    }

    public Manager(Long id, String label, String param1) {
        this.id = id;
        this.label = label;
        this.param1 = param1;
    }

    public Long getNo() {
        return id;
    }

    public void setNo(Long no) {
        this.id = no;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    @Override
    public String toString() {
        return "Manager{" + "no=" + id + ", label='" + label + '\'' + '}';
    }
}
