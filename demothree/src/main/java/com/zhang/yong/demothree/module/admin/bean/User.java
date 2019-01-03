package com.zhang.yong.demothree.module.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String salt;
    private Date created;
    private Date updated;
    private Set<String> roles = new HashSet<>();    //用户所有角色值，用于shiro做角色权限的判断
    private Set<String> permissions = new HashSet<>();    //用户所有权限值，用于shiro做资源权限的判断
    private List<Menu> menus;
}
