package edu.luxoft.dbservice;

import edu.luxoft.dbservice.annotations.Column;
import edu.luxoft.dbservice.annotations.Id;
import edu.luxoft.dbservice.annotations.Table;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryGenerator<T> {
    private Map<String, FieldMappingDescription> columnDescription = new LinkedHashMap<>();
    private Map<String, FieldMappingDescription> idColumnDescription = new LinkedHashMap<>();
    private String tableName;


    //-------------------------
    class QueryBuilder {

        private static final String SELECT = "SELECT";
        private static final String UPDATE = "UPDATE";
        private static final String DELETE = "DELETE";
        private static final String INSERT = "INSERT";
        private static final String VALUES = "VALUES";
        private static final String FROM = "FROM";
        private static final String WHERE = "WHERE";
        private static final String SET = "SET";


        private StringBuilder queryText = new StringBuilder("");

        public QueryBuilder select() {
            queryText
                    .append(SELECT)
                    .append(" ");
            return this;
        }

        public QueryBuilder insert() {
            queryText
                    .append(INSERT)
                    .append(" ");
            return this;
        }

        public QueryBuilder update() {
            queryText
                    .append(UPDATE)
                    .append(" ");
            return this;
        }

        public QueryBuilder delete() {
            queryText
                    .append(DELETE)
                    .append(" ");
            return this;
        }

        public QueryBuilder columns(String columns) {
            return columns(columns,""," ");
        }
        public QueryBuilder columns(String columns, String prefix, String suffix) {
            StringJoiner columnList = new StringJoiner(", ", prefix, suffix);
            getColumns().map(t->t.getKey())
                    .filter(t -> columns.contains(t) || columns.equals(""))
                    .forEach(t -> columnList.add(t));
            queryText.append(columnList.toString());
            return this;
        }



        public QueryBuilder columns() {
            return columns("");
        }

        public QueryBuilder from() {
            queryText
                    .append(FROM)
                    .append(" ");
            return this;
        }

        public QueryBuilder table() {
            queryText
                    .append(tableName)
                    .append(" ");
            return this;
        }

        public QueryBuilder where(T record, String columns) {
            Collection<String> colList = getColumnsList(columns);
            if (colList.size() == 0) {
                colList = idColumnDescription.keySet();
            }
            queryText
                    .append(WHERE)
                    .append(" ")
                    .append(getCondition(record, colList))
                    .append(" ");
            return this;
        }


        private Collection<String> getColumnsList(String list) {
            List<String> columnList =
                    getColumns()
                            .filter(t -> Arrays.stream(list.split(",")).anyMatch(c -> t.getKey().toUpperCase().equals(c.trim().toUpperCase())))
                            .map(t -> t.getKey())
                            .collect(Collectors.toList());
            return columnList;
        }

        private Stream<Map.Entry<String, FieldMappingDescription>> getColumns() {
            return Stream.concat(
                    columnDescription.entrySet().stream(),
                    idColumnDescription.entrySet().stream());
        }


        private String getCondition(T record, Collection<String> columnList) {
            StringJoiner condition =
                    getColumns()
                            .filter(t -> columnList.stream().anyMatch(c -> t.getKey().toUpperCase().equals(c.toUpperCase())))
                            .map(t -> t.getKey() + " = " + getFormattedValue(record, t.getValue()))
                            .collect(() -> new StringJoiner(" AND "), StringJoiner::add, StringJoiner::merge);
            return condition.toString();
        }

        private String getFormattedValue(T record, FieldMappingDescription t) {
            String result = "";
            try {
                Field field = t.getFieldClass().getDeclaredField(t.getObjName());
                Class fieldClass = field.getType();
                field.setAccessible(true);
                if (isNumeric(fieldClass)) {
                    result = field.get(record).toString();

                } else if (isBoolean(fieldClass)) {
                    result = field.getBoolean(record) ? "1" : "0";
                } else if (isCharSequence(fieldClass)) {
                    result = "'" + field.get(record).toString() + "'";
                }
                field.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        }



        public QueryBuilder set(T record, String setColumns) {
            Collection<String> colList = getColumnsList(setColumns);
            if (colList.size() == 0) {
                colList = columnDescription.keySet();
            }
            queryText
                    .append(SET)
                    .append(" ")
                    .append(getCondition(record, colList).replace(" AND ", ", "))
                    .append(" ");
            return this;
        }

        public QueryBuilder values(T record, String columns) {
            queryText
                    .append(VALUES)
                    .append(" (")
                    .append(getValues(record,getColumnsList(columns)))
                    .append(") ");
            return this;
        }

        private String getValues(T record, Collection<String> columnList) {
            StringJoiner values =
                    getColumns()
                            .map(t -> t.getValue())
                            .filter(t -> columnList.stream().anyMatch(c -> t.getDbName().toUpperCase().equals(c.toUpperCase()))||columnList.size()==0)
                            .map(t -> getFormattedValue(record, t))
                            .collect(() -> new StringJoiner(", "), StringJoiner::add, StringJoiner::merge);
            return values.toString();

        }

        @Override
        public String toString() {
            return queryText.toString().trim();
        }
    }

    private String getFirstNotEmpty(String s1, String s2) {
        return s1.trim().isEmpty() ? s2 : s1;
    }

    //------------

    private static boolean isSQLSupported(Class fieldClass){
        return isCharSequence(fieldClass)||
                isBoolean(fieldClass)||
                isNumeric(fieldClass);
    }

    private static boolean isCharSequence(Class fieldClass) {
        return fieldClass.equals(Character.class) ||
                fieldClass.equals(char.class) ||
                Arrays.stream(fieldClass.getInterfaces()).anyMatch(i->i.getName().contains("CharSequence"));
    }

    private static boolean isBoolean(Class fieldClass) {
        return fieldClass.equals(boolean.class);
    }

    private static boolean isNumeric(Class fieldClass) {
        return fieldClass.equals(int.class) || fieldClass.equals(Integer.class)||
                fieldClass.equals(double.class) || fieldClass.equals(Double.class)||
                fieldClass.equals(long.class) || fieldClass.equals(Long.class)||
                fieldClass.equals(float.class)|| fieldClass.equals(Float.class);
    }

    public void compile(Class<T> cls) {

        if (cls.isAnnotationPresent(Table.class)) {
            tableName = getFirstNotEmpty(cls.getAnnotation(Table.class).name(), cls.getName());
            tableName = tableName.substring(tableName.lastIndexOf('.') + 1);
        } else {
            return;
        }
        Function<Field, String> nameColumnField = (t)->getFirstNotEmpty(t.getAnnotation(Column.class).name(), t.getName());
        Function<Field, String> nameIdField = (t)->getFirstNotEmpty(t.getAnnotation(Id.class).name(), t.getName());
        Class superClass = cls;
        while (superClass != Object.class) {
            final Class sClass = superClass;
            columnDescription.putAll(Arrays.stream(superClass.getDeclaredFields())
                    .filter(t -> t.isAnnotationPresent(Column.class)&&
                                isSQLSupported(t.getType()))
                    .map(t->new FieldMappingDescription(nameColumnField.apply(t), t.getName(), sClass))
                    .collect(Collectors.toMap(t->t.getDbName(), t -> t)));

            idColumnDescription.putAll(Arrays.stream(superClass.getDeclaredFields())
                    .filter(t -> t.isAnnotationPresent(Id.class))
                    .map(t->new FieldMappingDescription(nameIdField.apply(t), t.getName(), sClass))
                    .collect(Collectors.toMap(t->t.getDbName(), t -> t)));
            superClass = superClass.getSuperclass();
        }
    }

    public String getSelect() {
        QueryBuilder query = new QueryBuilder()
                .select()
                .columns()
                .from()
                .table();

        return query.toString();
    }

    public String getDelete(T record) {
        return getDelete(record, "");
    }

    public String getDelete(T record, String whereColumns) {
        QueryBuilder query = new QueryBuilder()
                .delete()
                .from()
                .table()
                .where(record, whereColumns);
        return query.toString();
    }

    public String getUpdate(T record) {
        return getUpdate(record, "");
    }

    public String getUpdate(T record, String setColumns) {
        QueryBuilder query = new QueryBuilder()
                .update()
                .table()
                .set(record, setColumns)
                .where(record, "");
        return query.toString();
    }

    public String getInsert(T record) {
        return getInsert(record, "");
    }
    public String getInsert(T record, String insertColumns) {
        QueryBuilder query = new QueryBuilder()
                .insert()
                .table()
                .columns(insertColumns,"(",") ")
                .values(record,insertColumns);
        return query.toString();
    }
}
