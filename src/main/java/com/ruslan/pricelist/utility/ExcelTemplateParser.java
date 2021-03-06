package com.ruslan.pricelist.utility;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Item;
import com.ruslan.pricelist.exception.DistributorFileParsingException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        List<Item> items = new ArrayList<>();
        // Parsing excel file
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
                Date itemExpireDate = null;
                String itemProducer = null;
                try {
                    itemName = row.getCell(0).getStringCellValue();
                    itemPrice = row.getCell(1).getNumericCellValue();
                    itemExpireDate = row.getCell(2).getDateCellValue();
                    itemProducer = row.getCell(3).getStringCellValue();
                    item = new Item(
                            itemName
                            , itemPrice
                            , itemExpireDate
                            , itemProducer
                    );
                } catch (NullPointerException ex) {
                    if (itemName == null || itemName.length() == 0) {
                        throw new DistributorFileParsingException("Название номенклатуры на " + row.getRowNum() + " строке пустой, пожалуйста заполните все данные и загрузите файл снова!");
                    } else if (itemPrice == null) {
                        throw new DistributorFileParsingException("Цена продукта на " + row.getRowNum() + " строке пустой, пожалуйста заполните все данные и загрузите файл снова!");
                    }else {
                        throw new DistributorFileParsingException("Файл содержит пустые ячейки на "+row.getRowNum()+" строке, пожалуйста заполните все данные и загрузите файл снова!");
                    }
                }

                if (!items.contains(item))
                    items.add(item);
                rowNumber++;
            } else {
                break;
            }
        } while (true);
        if (items.size() == 0)
            throw new DistributorFileParsingException("Файл пустой, пожалуйста заполните все данные и загрузите файл снова!");
        distributor = new Distributor(distributorName, items);
        return distributor;
    }
}
