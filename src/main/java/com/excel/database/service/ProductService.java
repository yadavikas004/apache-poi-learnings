package com.excel.database.service;

import com.excel.database.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public void save(MultipartFile file);

    public List<Product> getAllProducts();
}
