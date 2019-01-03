package com.zhang.yong.demothree.module.admin.dao;

import com.zhang.yong.demothree.module.admin.bean.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface MenuMapper {
    @Select("select * from menu")
    List<Menu> selectAll();

    @Select("select * from menu where id=#{id}")
    Menu selectById(Long id);

    @Select("select * from menu where type=#{type}")
    List<Menu> selectByType(int type);

    @Select("select m.* from menu m right join role_menu rm on m.id=rm.menu_id where rm.role_id=#{roleId}")
    List<Menu> selectByRoleId(Long roleId);

    @Select("select distinct m.permission from menu m left join role_menu rm on m.id=rm.menu_id inner join " +
            "user_role ur on rm.role_id=ur.role_id where ur.user_id=#{userId} and m.type=1")
    Set<String> selectByUserId(Long userId);

    @Select("select distinct m.* from menu m left join role_menu rm on m.id=rm.menu_id inner join " +
            "user_role ur on rm.role_id=ur.role_id where ur.user_id=#{userId}")
    List<Menu> selectMenusByUserId(Long userId);

    @Insert("insert into menu values(#{id},#{name},#{icon},#{url},#{permission},#{type},#{parentId},#{canShow})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(Menu menu);

    @Update("update user set name=#{name},icon=#{icon},url=#{url},permission=#{permission}," +
            "type=#{type},parent_id=#{parentId},can_show=#{canShow} where id=#{id}")
    void update(Menu menu);
}
