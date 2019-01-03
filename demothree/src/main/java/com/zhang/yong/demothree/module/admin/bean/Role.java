package com.zhang.yong.demothree.module.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Role {
    private Long id;
    private String name;
    private List<Long> menuIds;
}
