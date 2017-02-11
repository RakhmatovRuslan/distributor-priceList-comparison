package com.ruslan.pricelist.utility;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Item;
import com.ruslan.pricelist.exception.DistributorFileParsingException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 12/29/2016.
 */
@Component
public class ExcelTemplateParser {

    public static File createTempDirectory()
            throws IOException {
        final File temp;

        temp = File.createTempFile(System.getProperty("java.io.tmpdir"), Long.toString(System.nanoTime()));

        if (!(temp.delete())) {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if (!(temp.mkdir())) {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);
    }


    public Distributor parseDistributorFile(File distributorFile) throws IOException, InvalidFormatException, DistributorFileParsingException {
        Distributor distributor = null;
        Item item = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        List<Item> items = new ArrayList<>();
        // Parsing excel file
        DataFormatter dataFormatter = new DataFormatter();
        dataFormatter.addFormat("#####.##_",new DecimalFormat());
        Workbook workbook = WorkbookFactory.create(distributorFile);
        Sheet sheet = workbook.getSheetAt(0);
        String distributorName = (sheet.getRow(0).getCell(2).getStringCellValue());
        if (distributorName == null)
            throw new DistributorFileParsingException("Вы забыли включить имя поставщика, пожалуйста напишите и загрузите файл снова!");
        int rowNumber = 2;
        do {
            Row row = sheet.getRow(rowNumber);
            if (row != null) {
                String itemName = null;
                Double itemPrice = null;
                String itemExpireDate = null;
                String itemProducer = null;

                try {
                    itemName = dataFormatter.formatCellValue(row.getCell(0));
                    itemPrice = getPriceFromCell(row.getCell(1));
                    itemProducer = dataFormatter.formatCellValue(row.getCell(3));
                    itemExpireDate = dataFormatter.formatCellValue(row.getCell(2));

                } catch (NullPointerException ex) {
                    rowNumber++;
                    continue;
                } catch (IllegalStateException ex) {
                    System.out.println(ex.getMessage());
                }
                item = new Item(
                        itemName
                        , itemPrice
                        , itemExpireDate
                        , itemProducer
                );
                if (item.getName().length() != 0&& item.getPrice() != null) {
                    items.add(item);
                }
                rowNumber++;
            } else {
                break;
            }
        } while (true);
        if (items.size() == 0)
            throw new DistributorFileParsingException(distributorFile.getName() + " пустой, пожалуйста заполните все данные и загрузите файл снова!");
        distributor = new Distributor(distributorName, items);
        return distributor;
    }
    
    
    private Double getPriceFromCell(Cell cell){
        DataFormatter dateFormatter = new DataFormatter();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                String price = dateFormatter.formatCellValue(cell);
                return Double.valueOf(price);
            case Cell.CELL_TYPE_NUMERIC:
                    return cell.getNumericCellValue();
            default:
                return cell.getNumericCellValue();
        }
    }
}
