package com.zhang.yong.demothree.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class DataTableParam {
    private int draw;
    private int start;
    private int length;
    private Search search;
    private List<Order> orders;
    private List<Column> columns;

    @Setter
    @Getter
    public static class Search {
        private String value;
        private boolean regex;
    }

    @Setter
    @Getter
    public static class Order {
        private int column;
        private String dir;
    }

    @Setter
    @Getter
    public static class Column {
        private String data;
        private String name;
        private boolean searchable;
        private boolean orderable;
        private Search search;
    }



    public static DataTableParam parseParamFromMap(Map<String,Object> map) {
        DataTableParam tableParam = new DataTableParam();
        Search search = new Search();
        List<Order> orders = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        tableParam.setDraw(convert(map,"draw",Integer.TYPE));
        tableParam.setStart(convert(map,"start",Integer.TYPE));
        tableParam.setLength(convert(map,"length",Integer.TYPE));
        search.setValue(convert(map,"search[value]",String.class));
        search.setRegex(convert(map,"search[regex]",Boolean.TYPE));
        tableParam.setSearch(search);
        tableParam.setOrders(orders);
        tableParam.setColumns(columns);
        map.forEach((key, value) -> {
            match(key,value,tableParam);
        });

        return tableParam;
    }

    @SuppressWarnings("unchecked")
    private static <T> T convert(Map<String,Object> map,String key, Class<T> clazz) {
        return (T)ConvertUtils.convert(map.get(key),clazz);
    }

    private static String order_regex = "^order\\[(\\d+)]\\[(column|dir)]$";
    private static String column_regex = "^columns\\[(\\d+)]\\[(name|data|searchable|orderable|)]$";
    private static String column_search_regex = "^columns\\[(\\d+)]\\[(search)]\\[(value|regex)]$";

    private static void match(String key, Object value, DataTableParam tableParam) {
        Pattern pattern = Pattern.compile(order_regex);
        Matcher matcher = pattern.matcher(key);
        if(matcher.find()) {
            matchOrder(matcher,value,tableParam.getOrders());
            return;
        }
        pattern = Pattern.compile(column_regex);
        matcher = pattern.matcher(key);
        if(matcher.find()) {
            matchColumn(matcher,value,tableParam.getColumns());
        }
        pattern = Pattern.compile(column_search_regex);
        matcher = pattern.matcher(key);
        if(matcher.find()) {
            matchColumnSearch(matcher,value,tableParam.getColumns());
        }
    }

    private static void matchOrder(Matcher matcher, Object value, List<Order> orders) {
        int index = Integer.valueOf(matcher.group(1));
        Order order = null;
        boolean isNew = false;
        try {
            order = orders.get(index);
        }catch (IndexOutOfBoundsException e) {
            isNew = true;
            order = new Order();
        }
        String prop = matcher.group(2);
        if(null != value) {
            if("column".equals(prop)){
                order.setColumn(Integer.valueOf(value.toString()));
            }else if("dir".equals(prop)) {
                order.setDir(value.toString());
            }
        }
        if(isNew) orders.add(order);
    }

    private static void matchColumn(Matcher matcher, Object value, List<Column> columns) {
        int index = Integer.valueOf(matcher.group(1));
        Column column = null;
        boolean isNew = false;
        try {
            column = columns.get(index);
        }catch (IndexOutOfBoundsException e) {
            isNew = true;
            column = new Column();
        }
        String prop = matcher.group(2);
        if(null != value) {
            if("name".equals(prop)){
                column.setName(value.toString());
            }else if("data".equals(prop)) {
                column.setData(value.toString());
            }else if("searchable".equals(prop)) {
                column.setSearchable(Boolean.valueOf(value.toString()));
            }else if("orderable".equals(prop)) {
                column.setOrderable(Boolean.valueOf(value.toString()));
            }
        }
        if(isNew) columns.add(column);
    }

    private static void matchColumnSearch(Matcher matcher, Object value, List<Column> columns) {
        int index = Integer.valueOf(matcher.group(1));
        Column column = null;
        boolean isNew = false;
        try {
            column = columns.get(index);
        }catch (IndexOutOfBoundsException e) {
            isNew = true;
            column = new Column();
        }
        String prop = matcher.group(2);
        String subProp = matcher.group(3);
        Search search = column.getSearch();
        if(null == search) search = new Search();
        if(null != value) {
            if("value".equals(subProp)){
                search.setValue(value.toString());
            }else if("regex".equals(subProp)) {
                search.setRegex(Boolean.valueOf(value.toString()));
            }
        }
        column.setSearch(search);
        if(isNew) columns.add(column);
    }
}
