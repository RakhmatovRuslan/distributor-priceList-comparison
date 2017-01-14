package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Nomenclature;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ruslan on 12/30/2016.
 */
public interface PriceComparisonService {

    List<Distributor> getDistributorsPriceForItem(String nomenclatureName, List<Distributor> distributors);

    void generateDistributorsPriceListComparison(List<Nomenclature> nomenclatures, List<Distributor> allDistributors, File newFile) throws IOException, InvalidFormatException;
}
