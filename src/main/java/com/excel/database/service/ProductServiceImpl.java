package com.excel.database.service;

import com.excel.database.entity.Product;
import com.excel.database.helper.ExcelHelper;
import com.excel.database.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void save(MultipartFile file) {
        try {
           List<Product> products = ExcelHelper.convertExcelToListOfProduct(file.getInputStream());
           this.productRepository.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
