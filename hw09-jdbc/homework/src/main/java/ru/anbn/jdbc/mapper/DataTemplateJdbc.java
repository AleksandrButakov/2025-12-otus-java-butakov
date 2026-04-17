package ru.anbn.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.anbn.core.repository.DataTemplate;
import ru.anbn.core.repository.DataTemplateException;
import ru.anbn.core.repository.executor.DbExecutor;

@SuppressWarnings("java:S3011")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (!rs.next()) return null;
                T obj = entityClassMetaData.getConstructor().newInstance();
                for (Field field : entityClassMetaData.getAllFields()) {
                    // Используем рефлексию для учебного ORM, в продакшне лучше сеттеры/MapStruct
                    field.setAccessible(true);
                    Object value = rs.getObject(field.getName());
                    field.set(obj, value);
                }
                return obj;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
                    List<T> list = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            T obj = entityClassMetaData.getConstructor().newInstance();
                            for (Field field : entityClassMetaData.getAllFields()) {
                                field.setAccessible(true);
                                Object value = rs.getObject(field.getName());
                                field.set(obj, value);
                            }
                            list.add(obj);
                        }
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                    return list;
                })
                .orElse(List.of());
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Object> params = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
            long generatedId = dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);

            // устанавливаем ID обратно в объект
            Field idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            idField.set(client, generatedId);
            return generatedId;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Object> params = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
            Field idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            params.add(idField.get(client));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
