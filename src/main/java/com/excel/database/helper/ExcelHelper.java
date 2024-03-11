package com.excel.database.helper;

import com.excel.database.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file){
        String contentType= file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }else {
            return false;
        }
    }

    //convert excel to list of products

    public static List<Product> convertExcelToListOfProduct(InputStream is){
        List<Product> list = new ArrayList<>();

        try{

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Products");
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while(iterator.hasNext()){
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                Product p = new Product();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch (cid){
                        case 0:
                             p.setProductId((int)cell.getNumericCellValue());
                            System.out.println(p.getContact());
                             break;
                        case 1:
                            p.setProductName(cell.getStringCellValue());
                            break;
                        case 2:
                            p.setProductDesc(cell.getStringCellValue());
                            break;
                        case 3:
                            p.setProductPrice(cell.getNumericCellValue());
                            break;
                        case 4:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                p.setContact(String.valueOf((long) cell.getNumericCellValue()));
                            }else if (cell.getCellType() == CellType.STRING) {
                                p.setContact(cell.getStringCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(p);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

}
