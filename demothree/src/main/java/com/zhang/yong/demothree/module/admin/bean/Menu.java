package com.zhang.yong.demothree.module.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Menu {
    private Long id;
    private String name;
    private String icon;
    private String url;
    private String permission;
    private boolean type;
    private Long parentId;
    private boolean canShow;
    private List<Menu> subMenus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
