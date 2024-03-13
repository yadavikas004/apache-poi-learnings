package com.excel.database.controller;

import com.excel.database.entity.Product;
import com.excel.database.helper.ExcelHelper;
import com.excel.database.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("file")MultipartFile file
            ){
        if (ExcelHelper.checkExcelFormat(file)) {
            //true
            this.productService.save(file);
            return ResponseEntity.ok(Map.of("message","File is uploaded and data is saved into db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Upload excel file only with one sheet");
    }

    @GetMapping("/list")
    public List<Product> getAllProduct(){
        return this.productService.getAllProducts();
    }
}
