package ru.anbn.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация EntitySQLMetaData.
 * Генерирует SQL для конкретного класса (таблицы) на основе метаданных.
 */
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    private final String columnList;

    private final EntityClassMetaData<?> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.classMetaData = classMetaData;

        this.columnList =
                classMetaData.getAllFields().stream().map(Field::getName).collect(Collectors.joining(", "));

        this.selectAllSql = initSelectAllSql();
        this.selectByIdSql = initSelectByIdSql();
        this.insertSql = initInsertSql();
        this.updateSql = initUpdateSql();
    }

    private String initSelectAllSql() {
        return "SELECT " + columnList + " FROM " + getTableName();
    }

    private String initSelectByIdSql() {
        return "SELECT " + columnList + " FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
    }

    private String initInsertSql() {
        List<String> columns =
                classMetaData.getFieldsWithoutId().stream().map(Field::getName).toList();

        String columnNames = String.join(", ", columns);
        String placeholders = columns.stream().map(c -> "?").collect(Collectors.joining(", "));

        return "INSERT INTO " + getTableName() + "(" + columnNames + ") VALUES(" + placeholders + ")";
    }

    private String initUpdateSql() {
        String setClause = classMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        return "UPDATE " + getTableName() + " SET " + setClause + " WHERE " + getIdColumn() + " = ?";
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }

    private String getTableName() {
        return classMetaData.getName().toLowerCase();
    }

    private String getIdColumn() {
        return classMetaData.getIdField().getName();
    }
}
