package com.zhang.yong.demothree.module.admin.service;

import com.zhang.yong.demothree.module.admin.bean.User;
import com.zhang.yong.demothree.module.admin.dao.UserMapper;
import com.zhang.yong.demothree.util.CollectionUtil;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAll() {
//        jdbcTemplate.query("",(resultSet) -> {
//            resultSet.getString(0);
//        });
        return userMapper.selectAll();
    }

    public User getByName(String username) {
        return userMapper.selectByName(username);
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Transactional
    public void saveOrUpdate(User user) {
        if(null == user.getId()) {
            fillUserPassword(user);
            user.setCreated(new Date());
            userMapper.insert(user);
        }else{
            user.setUpdated(new Date());
            userMapper.update(user);
            userMapper.deleteUserRoleByUserId(user.getId());
        }
        if(CollectionUtil.notEmpty(user.getRoleIds())) {
            userMapper.insertUserRole(user.getId(),user.getRoleIds());
        }
    }

    private void fillUserPassword(User user) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        String salt = rng.nextBytes().toString();
        //此处的加密算法要与shiro框架使用的加密算法一致，shiro配置 见类CustomRealm 代码块
        String hashedPasswordBase64 = new Sha256Hash(user.getPassword(), salt, 1024).toBase64();
        user.setSalt(salt);
        user.setPassword(hashedPasswordBase64);
    }

    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }
}
