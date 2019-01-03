package com.zhang.yong.demothree.module.admin.dao;

import com.zhang.yong.demothree.module.admin.bean.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    @Insert("insert into product(id, name) values(#{id},#{name})")
    void insert(Product product);

    @Update("update product set name=#{name} where id=#{id}")
    Long update(Product product);

    @Delete("delete from product where id=#{id}")
    Long delete(@Param("id") String id);

    @Select("select id, name from product")
    List<Product> selectAll();

    @Select("select id, name from product where id=#{id}")
    Product selectById(@Param("id") String id);
}
