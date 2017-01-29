package com.ruslan.pricelist.utility;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Item;
import com.ruslan.pricelist.beans.Nomenclature;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ruslan on 1/13/2017.
 */
@Component
public class ExcelComparisonResultParser {
    private XSSFWorkbook workbook;

    public void generateExcel(List<Nomenclature> nomenclaturesFromFile, List<Distributor> allDistributors, File newFile) throws IOException, InvalidFormatException {
        newFile.deleteOnExit();
        Collections.sort(nomenclaturesFromFile);

        if (!newFile.createNewFile()) {

            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Сравнение цен дистрибюторов");

            int rowNumber = 0;
            int cellNumber = 0;
            Row row;
            Cell cell;
            //------------------------------header row-------------------------------------------------------
            row = sheet.createRow(rowNumber);
            //------------№ header-------------
            cell = row.createCell(cellNumber,CellType.NUMERIC);
            cell.setCellValue("№");
            cell.setCellStyle(createStyles().get("header"));
            cellNumber++;
            //-------------Nomenclature header-----------
            cell = row.createCell(cellNumber, CellType.STRING);
            cell.setCellValue("Номенклатура");
            cell.setCellStyle(createStyles().get("header"));
            cellNumber++;
            //-------------------------------------------
            for (int i = 0; i < allDistributors.size(); i++) {
                cell = row.createCell(i + cellNumber, CellType.STRING);
                cell.setCellValue(allDistributors.get(i).getName());
                cell.setCellStyle(createStyles().get("header"));
            }
            //------------------------------header row-------------------------------------------------------
            rowNumber++;
            //------------------------------content----------------------------------------------------------
            for (Nomenclature nomenclature : nomenclaturesFromFile) {
                row = sheet.createRow(rowNumber);
                row.setHeightInPoints(15.75f);
                cellNumber = 0;
                //---------Nomenclature id----------------
                cell = row.createCell(cellNumber);
                cell.setCellStyle(createStyles().get("nomenclature"));
                cell.setCellValue(rowNumber);
                cellNumber++;
                //---------Nomenclature name---------------
                cell = row.createCell(cellNumber, CellType.STRING);
                cell.setCellStyle(createStyles().get("nomenclature"));
                cell.setCellValue(nomenclature.getName());
                cellNumber++;
                //----------------------------------------
                //distributor cell`s hashMap
                // declare cell index of the cheapest item price
                Double cheapest = 0.0;
                // declare cell index of the expensive item price
                Double expensive = 0.0;
                for (int i = 0; i < allDistributors.size(); i++) {
                    // compare nomenclature with item name
                    List<Item> items = allDistributors.get(i).getPriceList();
                    int itemIndex = items.indexOf(new Item(nomenclature.getName()));

                    if (itemIndex != -1) {
                        cell = row.createCell(i + cellNumber);
                        cell.setCellValue(items.get(itemIndex).getPrice());
                        cell.setCellStyle(createStyles().get("currency"));
                        if(cheapest == 0.0)
                            cheapest = cell.getNumericCellValue();
                        // define the best price
                        if (cheapest > cell.getNumericCellValue()) {
                            cheapest = cell.getNumericCellValue();
                        }
                        //define the expensive price
                        if (expensive < cell.getNumericCellValue()) {
                            expensive = cell.getNumericCellValue();
                        }
                    }else{
                        cell = row.createCell(i+cellNumber);
                        cell.setCellValue(0.0);
                        cell.setCellStyle(createStyles().get("currency"));
                    }
                }
                for(int i=2; i< row.getPhysicalNumberOfCells(); i++){
                    cell = row.getCell(i);
                    if(cheapest != 0.0 && cheapest != expensive){
                        if(cell.getNumericCellValue() == cheapest)
                            cell.setCellStyle(setFontColorCellStyle(IndexedColors.GREEN.index));
                        else if(cell.getNumericCellValue() == expensive)
                            cell.setCellStyle(setFontColorCellStyle(IndexedColors.RED.index));
                    }
                }
                rowNumber++;
            }
            //------------------------------set column width-------------------------------------------------
            cellNumber = 0;
            sheet.setColumnWidth(cellNumber,256*6);
            cellNumber++;
            sheet.setColumnWidth(cellNumber,256*50);
            cellNumber++;
//            sheet.setDefaultRowHeight((short) 5);
            for (int i = 0; i < allDistributors.size(); i++) {
                sheet.setColumnWidth(cellNumber+i,256*25);
            }
            //------------------------------set column width-------------------------------------------------

            //------------------------------content----------------------------------------------------------
            FileOutputStream fout = new FileOutputStream(newFile);
            workbook.write(fout);
            workbook.close();
            fout.close();
        }
    }


    private CellStyle setFontColorCellStyle(short colorIndex) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(colorIndex);
        DataFormat dataFormat = workbook.createDataFormat();
        style = createBorderedStyle(workbook);
        style.setDataFormat(dataFormat.getFormat("#,##0.0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    private HashMap<String, CellStyle> createStyles() {
        HashMap<String, CellStyle> styles = new HashMap<>();

        CellStyle style;
        //-------------header style-------------------------------
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);
        //-------------header style-------------------------------

        //-------------currency format----------------------------
        DataFormat dataFormat = workbook.createDataFormat();
        style = createBorderedStyle(workbook);
        style.setDataFormat(dataFormat.getFormat("#,##0.0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("currency",style);
        //---------------------------------------------------------
        //--------------nomenclature-------------------------------
        style = createBorderedStyle(workbook);
        styles.put("nomenclature",style);
        return styles;
    }

    private static CellStyle createBorderedStyle(Workbook wb){
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();

        CellStyle style = wb.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }
}
