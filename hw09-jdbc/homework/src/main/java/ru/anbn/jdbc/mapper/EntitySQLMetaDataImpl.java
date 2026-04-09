package ru.anbn.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация EntitySQLMetaData.
 * Генерирует SQL для конкретного класса (таблицы) на основе метаданных.
 */
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + getTableName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
    }

    @Override
    public String getInsertSql() {
        List<String> columns = classMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();

        String columnNames = String.join(", ", columns);
        String placeholders = columns.stream().map(c -> "?").collect(Collectors.joining(", "));

        return "INSERT INTO " + getTableName() + "(" + columnNames + ") VALUES(" + placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        List<String> setClauses = classMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .toList();

        String setClause = String.join(", ", setClauses);
        return "UPDATE " + getTableName() + " SET " + setClause + " WHERE " + getIdColumn() + " = ?";
    }

    private String getTableName() {
        return classMetaData.getName().toLowerCase();
    }

    private String getIdColumn() {
        return classMetaData.getIdField().getName();
    }
}
