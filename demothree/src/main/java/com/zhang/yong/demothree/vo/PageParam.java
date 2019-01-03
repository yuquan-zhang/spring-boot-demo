package com.zhang.yong.demothree.vo;

import com.zhang.yong.demothree.util.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class PageParam {
    /**
     * 绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）
     */
    private int draw;
    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 分页合理化
     */
    private boolean reasonable;
    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     */
    private boolean pageSizeZero;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 包含count查询
     */
    private boolean count;

    public static PageParam instance(DataTableParam tableParam) {
        PageParam pageParam = new PageParam();
        pageParam.setDraw(tableParam.getDraw());
        pageParam.setPageSize(tableParam.getLength());
        if(tableParam.getLength() > 0) {
            pageParam.setPageNum(tableParam.getStart() / tableParam.getLength() + 1);
        }else if(tableParam.getLength() < 0){
            pageParam.setPageSize(0);
            pageParam.setPageSizeZero(true);
        }
        StringBuilder orderBy = new StringBuilder();
        if(CollectionUtil.notEmpty(tableParam.getOrders())) {
            tableParam.getOrders().forEach(order -> {
                String column = tableParam.getColumns().get(order.getColumn()).getData();
                if(orderBy.length() != 0) orderBy.append(",");
                orderBy.append(sortChinese(column)).append(" ").append(order.getDir());
            });
        }
        pageParam.setOrderBy(orderBy.toString());
        return pageParam;
    }

    /**
     * 增加按中文排序
     * @param column
     * @return
     */
    private static String sortChinese(String column) {
        String convert = camelToUnderscore(column);
        // mysql 中文排序 convert(url using gbk) [collate gbk_chinese_ci]
        return String.format("convert(%s using gbk) collate gbk_chinese_ci",convert);
    }

    /**
     * 将驼峰字符串转为下划线分割字符串，例如"canShow" -》 "can_show";
     * @param camel
     * @return
     */
    private static String camelToUnderscore(String camel) {
        return camel.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }

    public static PageParam instance(Map<String,Object> map) {
        DataTableParam tableParam = DataTableParam.parseParamFromMap(map);
        return instance(tableParam);
    }
}
