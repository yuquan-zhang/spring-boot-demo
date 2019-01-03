package com.zhang.yong.demothree.module.admin.service;

import com.zhang.yong.demothree.module.admin.bean.Product;
import com.zhang.yong.demothree.module.admin.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public void createProduct(Product product) {
        productMapper.insert(product);
    }

    public void updateProduct(String id, Product product) {
        productMapper.update(product);
    }

    public void deleteProduct(String id) {
        productMapper.delete(id);
    }

    public Collection<Product> getProducts() {
        return productMapper.selectAll();
    }

    public Product getById(String id) {
        return productMapper.selectById(id);
    }
}
