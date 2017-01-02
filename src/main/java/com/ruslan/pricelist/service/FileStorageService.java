package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.exception.DistributorFileParsingException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ruslan on 12/29/2016.
 */
public interface FileStorageService {
    /**
     * Distributors price list .xlsx file parsing and storing in database
     * Arguments: MultipartFile uploaded by user (extension mest be .xlsx while storing)
     */
     void storeFile(MultipartFile file) throws IOException, DistributorFileParsingException, InvalidFormatException;

    List<Distributor> getAllDistributors();

    void remove(Long id);

    void removeAll();
}
