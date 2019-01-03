package com.zhang.yong.demothree.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhang.yong.demothree.vo.PageParam;

public class PageUtil {
    public static <E> Page<E> startPage(PageParam pageParam) {
        Page<E> page = PageHelper.startPage(pageParam);
        if(pageParam.isPageSizeZero()) {
            page.setOrderByOnly(true);
        }
        return page;
    }
}
