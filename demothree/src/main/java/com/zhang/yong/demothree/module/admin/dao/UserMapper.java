package com.zhang.yong.demothree.module.admin.dao;


import com.zhang.yong.demothree.module.admin.bean.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user")
    List<User> selectAll();

    @Select("select * from user where username=#{username}")
    User selectByName(String username);

    @Select("select * from user where id=#{id}")
    User selectById(Long id);

    @Insert("insert into user values(#{id},#{username},#{nickname},#{password},#{salt},#{created},#{updated})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(User user);

    @Update("update user set username=#{username},nickname=#{nickname},password=#{password},salt=#{salt}," +
            "created=#{created},updated=#{updated} where id=#{id}")
    void update(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(Long id);

    @Insert("<script>insert into user_role(user_id,role_id) values " +
            "<foreach item='item' collection='roleIds' separator=','> (#{userId},#{item})</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    @Delete("delete from user_role where user_id=#{userId}")
    void deleteUserRoleByUserId(Long userId);
}
