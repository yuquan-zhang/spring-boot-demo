package com.zhang.yong.demothree.module.admin.dao;

import com.zhang.yong.demothree.module.admin.bean.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface RoleMapper {
    @Select("select * from role")
    List<Role> selectAll();

    @Select("select * from role where id=#{id}")
    Role selectById(Long id);

    @Select("select * from role where name=#{name}")
    Role selectByName(String name);

    @Select("select distinct r.name from role r right join user_role ur on r.id = ur.role_id where ur.user_id=#{userId}")
    Set<String> selectByUserId(Long userId);

    @Select("select r.* from role r right join user_role ur on r.id = ur.role_id where ur.user_id=#{userId}")
    List<Role> selectRolesByUserId(Long userId);

    @Insert("insert into role values(#{id},#{name})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(Role role);

    @Update("update role set name=#{name} where id=#{id}")
    void update(Role role);

    @Insert("<script>insert into role_menu(role_id,menu_id) values " +
            "<foreach item='item' collection='menuIds' separator=','> (#{roleId},#{item})</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    @Delete("delete from role_menu where role_id=#{roleId}")
    void deleteRoleMenuByRoleId(Long roleId);
}
