package ru.anbn.jdbc.mapper;

import ru.anbn.core.repository.DataTemplateException;
import ru.anbn.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.constructor = initConstructor();
        this.allFields = List.of(clazz.getDeclaredFields());
        this.idField = initIdField();
        this.fieldsWithoutId = initFieldsWithoutId();
    }

    private Constructor<T> initConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new DataTemplateException(e);
        }
    }

    private Field initIdField() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Id field not found in " + clazz.getName()));
    }

    private List<Field> initFieldsWithoutId() {
        return allFields.stream().filter(f -> !f.equals(idField)).toList();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
