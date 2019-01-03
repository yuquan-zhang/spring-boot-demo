package com.zhang.yong.demothree.tag;

import java.util.Map;

public class PermissionButtonTag extends PermissionTag {
    @Override
    protected void subRender(Map<String, String> attrs) {
        attrs.put("tagType","a");
        attrs.put("class","mini-button");
        super.subRender(attrs);
    }
}
