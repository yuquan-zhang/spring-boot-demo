package com.zhang.yong.demothree.util;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isEmpty(Collection<?>  c) {
        return null == c || c.size() == 0;
    }

    public static boolean notEmpty(Collection<?>  c) {
        return !isEmpty(c);
    }
}
