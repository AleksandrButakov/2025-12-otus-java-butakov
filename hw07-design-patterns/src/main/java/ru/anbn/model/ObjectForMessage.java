package ru.anbn.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage() {
        this.data = new ArrayList<>();
    }

    public ObjectForMessage(List<String> data) {
        this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
    }

    public ObjectForMessage copy() {
        return new ObjectForMessage(new ArrayList<>(this.data));
    }
}
