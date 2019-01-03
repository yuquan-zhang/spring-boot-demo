package com.zhang.yong.demothree.module.admin.service;

import com.zhang.yong.demothree.module.admin.bean.Role;
import com.zhang.yong.demothree.module.admin.dao.RoleMapper;
import com.zhang.yong.demothree.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getAll() {
        return roleMapper.selectAll();
    }

    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    public Role getByName(String name) {
        return roleMapper.selectByName(name);
    }

    public Set<String> getRolesByUserId(Long userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Transactional
    public void saveOrUpdate(Role role) {
        if(null == role.getId()) {
            roleMapper.insert(role);
        }else{
            roleMapper.update(role);
            roleMapper.deleteRoleMenuByRoleId(role.getId());
        }
        if(CollectionUtil.notEmpty(role.getMenuIds())) {
            roleMapper.insertRoleMenu(role.getId(),role.getMenuIds());
        }
    }
}
