package com.ruslan.pricelist.controller;

import com.ruslan.pricelist.service.FileStorageService;
import com.ruslan.pricelist.service.NomenclatureService;
import com.ruslan.pricelist.service.PriceComparisonService;
import org.apache.commons.fileupload.FileUpload;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Ruslan on 1/2/2017.
 */
@Controller
@RequestMapping("/download")
public class FileDownloadController {
    @Autowired
    PriceComparisonService comparisonService;
    @Autowired
    NomenclatureService nomenclatureService;
    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("file_name") String fileName, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if(fileName.trim().toLowerCase().equals("template"))
              fileName = FileDownloadController.class.getClassLoader().getResource("template.xlsx").getFile();
            File file = new File(fileName);
            xlsxFileUploadResponse(file,response);
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @RequestMapping(value = "/comparison", method = RequestMethod.POST)
    public void generateComparisonReport(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            StringBuffer dataDirectory = new StringBuffer(request.getServletContext().getRealPath("/WEB-INF/downloads/"));
            //----generating file according to the current date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            File file = new File(dataDirectory.append(simpleDateFormat.format(new Date())).append(".xlsx").toString());
            file.deleteOnExit();
            file.createNewFile();
            nomenclatureService.generateNomenclaturesByPriceLists(fileStorageService.getAllDistributors());
            comparisonService.generateDistributorsPriceListComparison(nomenclatureService.getNomenclatures(),fileStorageService.getAllDistributors(),file);
            xlsxFileUploadResponse(file,response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean xlsxFileUploadResponse(File file, HttpServletResponse response) throws IOException
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+ file.getName());
        if(file.exists()){
            InputStream is = new FileInputStream(file);;
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            return true;
        }
        return false;
    }

}
