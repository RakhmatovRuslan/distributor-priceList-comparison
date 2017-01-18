package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Message;
import com.ruslan.pricelist.exception.DistributorFileParsingException;
import com.ruslan.pricelist.utility.ExcelTemplateParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ruslan on 12/29/2016.
 */
@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {
    //Temporarily storing distributor file content as a static List
    private static final String[] ALLOWED_FILE_TYPES = {"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
    static List<Distributor> distributors = new ArrayList<>();
    static Long id = 0L;


    @Autowired
    ExcelTemplateParser templateParser;

    @Override
    public Message storeFile(MultipartFile file) throws DistributorFileParsingException {
        if (!file.isEmpty()) {
            String contentType = file.getContentType().toString().toLowerCase();
            if (isValidContentType(contentType)) {
                // Throws IOException when there is an error in creating tmp directory
                try {
                    File distributorFile = new File(ExcelTemplateParser.createTempDirectory().getAbsolutePath(), file.getOriginalFilename());
                    file.transferTo(distributorFile);
                    // The content of the distributorFile is parsed and stored in Distributor class
                    Distributor distributor = templateParser.parseDistributorFile(distributorFile);
                    distributor.setId(++id);
                    if (!distributors.contains(distributor)) {
                        distributors.add(distributor);
                    } else {
                        return new Message("Вы не можете добавить прайлит " + distributor.getName() + " поставщика несколько раз!", false);
                    }
                    return new Message("Вы успешно загрузили шаблон " + file.getOriginalFilename() + " !", true); //success
                } catch (InvalidFormatException e) {
                    return new Message("Произошла ошибка при загрузке шаблона " + file.getOriginalFilename() + " !", false); //error
                } catch (IOException e) {
                    return new Message("Произошла ошибка при загрузке шаблона " + file.getOriginalFilename() + " !", false); //error
                }
            } else {
                return new Message("Oшибка. Шаблон " + file.getOriginalFilename() + " не допустимого формата! ", false);
            }
        } else {
            return new Message("Шаблон " + file.getOriginalFilename() + "не может быть пустым!", false);
        }
    }

    @Override
    public List<Distributor> getAllDistributors() {
        return distributors;
    }

    @Override
    public void remove(Long id) {
        for (int i = 0; i < distributors.size(); i++) {
            if (distributors.get(i).getId() == id) {
                distributors.remove(i);
                break;
            }
        }
    }

    @Override
    public void removeAll() {
        distributors.clear();
        id = 0L;
    }

    private Boolean isValidContentType(String contentType) {
        if (!Arrays.asList(ALLOWED_FILE_TYPES).contains(contentType)) {
            return false;
        }

        return true;
    }

}
