package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.exception.DistributorFileParsingException;
import com.ruslan.pricelist.utility.ExcelTemplateParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 12/29/2016.
 */
@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService{
    //Temporarily storing distributor file content as a static List
    //TODO store distributors in the database
    static List<Distributor> distributors = new ArrayList<>();
    static Long id = 0L;

    @Autowired
    ExcelTemplateParser templateParser;

    @Override
    public void storeFile(MultipartFile file) throws IOException, DistributorFileParsingException, InvalidFormatException {
        // Throws IOException when there is an error in creating tmp directory
        File distributorFile = new File(ExcelTemplateParser.createTempDirectory().getAbsolutePath(),file.getOriginalFilename());
        file.transferTo(distributorFile);
        // The content of the distributorFile is parsed and stored in Distributor class
        Distributor distributor = templateParser.parseDistributorFile(distributorFile);
        //TODO удалить после включение базы данных
        distributor.setId(++id);
        if(distributors.contains(distributor))
           throw new DistributorFileParsingException("Вы не можете добавить прайлит одного и того же поставщика несколько раз!");
        distributors.add(distributor);
    }

    @Override
    public List<Distributor> getAllDistributors() {
        return distributors;
    }

    @Override
    public void remove(Long id) {
        for (int i = 0; i < distributors.size(); i++) {
            if(distributors.get(i).getId() == id) {
                distributors.remove(i);
                break;
            }
        }
    }

    @Override
    public void removeAll() {
        distributors.clear();
    }
}
