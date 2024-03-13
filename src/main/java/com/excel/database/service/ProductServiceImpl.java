package com.excel.database.service;

import com.excel.database.entity.Product;
import com.excel.database.helper.ExcelHelper;
import com.excel.database.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void save(MultipartFile file) {
        try {
            List<String> failedRecords = new ArrayList<>();
            // Read data from the Excel file
            List<Product> products = ExcelHelper.readProductsFromExcel(file,failedRecords);

            // Track the count of records added and not added
            int addedCount = 0;
            int notAddedCount = 0;


            // Separate unique and duplicate records
            List<Product> uniqueProducts = new ArrayList<>();
            List<Product> duplicateProducts = new ArrayList<>();


            for (Product product : products) {
                // Check if the record already exists in the database
                if (!productRepository.existsByProductNameAndContact(product.getProductName(), product.getContact())) {
                    uniqueProducts.add(product);
                    addedCount++;
                } else {
                    duplicateProducts.add(product);
                    notAddedCount++;
                }
            }

            // Save unique records to the database
            productRepository.saveAll(uniqueProducts);

            // Generate an Excel file for duplicate records
            ExcelHelper.writeProductsToExcel(duplicateProducts,failedRecords, "duplicate_records.xlsx");

            System.out.println("Added count: " + addedCount);
            System.out.println("Not added count: " + notAddedCount);

        } catch (IOException e) {
            throw new RuntimeException("Exception occurs in ProductServiceImpl " + e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
