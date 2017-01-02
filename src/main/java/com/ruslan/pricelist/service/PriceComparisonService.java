package com.ruslan.pricelist.service;

import com.ruslan.pricelist.beans.Distributor;

import java.util.List;

/**
 * Created by Ruslan on 12/30/2016.
 */
public interface PriceComparisonService {

    List<Distributor> getDistributorsPriceForItem(String nomenclatureName, List<Distributor> distributors);
}
