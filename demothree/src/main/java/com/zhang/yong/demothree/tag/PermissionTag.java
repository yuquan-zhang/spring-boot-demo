package com.zhang.yong.demothree.tag;

import com.zhang.yong.demothree.ext.ShiroExt;
import org.beetl.core.BodyContent;
import org.beetl.core.Tag;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PermissionTag extends Tag {
    @Override
    @SuppressWarnings("unchecked")
    public void render() {
        if(this.args == null) return;
        Map<String,String> attrs = new HashMap<>();
        if(this.args.length >= 2) attrs = (Map<String, String>)args[1];
        String permissionKey = attrs.get("permissionKey");
        if(!StringUtils.isEmpty(permissionKey) && !ShiroExt.getInstance().hasPermission(permissionKey)){
            return;
        }
        subRender(attrs);
        BodyContent content = getBodyContent();
        String tagType = attrs.get("tagType");
        if(tagType == null || tagType.length() == 0) {
            tagType = "div";
        }
        StringBuilder sb = new StringBuilder();
        attrs.forEach((key, value) -> {
            if("permissionKey".equals(key) || "tagType".equals(key)) return;
            value = value.replaceAll("\"","'");
            sb.append(" ").append(key).append("=").append("\"").append(value).append("\"");
        });
        try {
            ctx.byteWriter.writeString(String.format("<%1$s%2$s>%3$s</%1$s>",tagType,sb.toString(),content.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void subRender(Map<String,String> attrs){
        // 父类不做任何操作；留给子类修改参数
    }
}
