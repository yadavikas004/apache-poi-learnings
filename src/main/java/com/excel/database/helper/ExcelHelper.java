package com.excel.database.helper;

import com.excel.database.entity.Product;
import com.excel.database.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@AllArgsConstructor
public class ExcelHelper {

    @Autowired
    private static ProductRepository productRepository;

    //check that file is of Excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if( contentType != null ){
            return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") || contentType.equals("text/csv");
        }
        return false;
    }

    //convert excel to list of products
    public static List<Product> readProductsFromExcel(MultipartFile file,List<String> failedRecords ) throws IOException {
        List<Product> products = new ArrayList<>();
        Map<String, Set<Long>> productContactsMap = new HashMap<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                Product product = new Product();
                product.setProductId((int) row.getCell(0).getNumericCellValue());
                product.setProductName(row.getCell(1).getStringCellValue());
                product.setProductDesc(row.getCell(2).getStringCellValue());
                product.setProductPrice(row.getCell(3).getNumericCellValue());
//                product.setContact((long) row.getCell(4).getNumericCellValue());

                // Validate and set contact number
                Cell contactCell = row.getCell(4);
                if (contactCell.getCellType() == CellType.NUMERIC) {
                    double contactValue = contactCell.getNumericCellValue();
                    long contact = (long) contactValue;
                    String productName = product.getProductName();

                    // Check if the contact number is associated with the product name
                    if (!productContactsMap.containsKey(productName)) {
                        productContactsMap.put(productName, new HashSet<>());
                    }

                    Set<Long> contactsForProduct = productContactsMap.get(productName);

                    if (!contactsForProduct.contains(contact)) {
                        // If the contact number is not associated with the product name, add it
                        contactsForProduct.add(contact);
                        product.setContact(contact);
                        products.add(product);
                    } else {
                        // If the contact number is associated with the product name, it's a duplicate
                        failedRecords.add("Duplicate record: " + productName + " - " + contact);
                    }
                } else {
                    failedRecords.add("Invalid contact number format: " + contactCell.getStringCellValue());
                }
//                products.add(product);
            }
            workbook.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid contact number format in Excel file");
        }
        return products;
    }


    public static void writeProductsToExcel(List<Product> products, List<String> failedRecords, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Product ID");
        headerRow.createCell(1).setCellValue("Product Name");
        headerRow.createCell(2).setCellValue("Product Description");
        headerRow.createCell(3).setCellValue("Product Price");
        headerRow.createCell(4).setCellValue("Contact");
        headerRow.createCell(5).setCellValue("Remarks");

        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(product.getProductId());
            row.createCell(1).setCellValue(product.getProductName());
            row.createCell(2).setCellValue(product.getProductDesc());
            row.createCell(3).setCellValue(product.getProductPrice());
            row.createCell(4).setCellValue(product.getContact());
            row.createCell(5).setCellValue("Added to database");
        }

        // Write failed records with remarks
        for (String failedRecord : failedRecords) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(5).setCellValue(failedRecord);
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }
}
